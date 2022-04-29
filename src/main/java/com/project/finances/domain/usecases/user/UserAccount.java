package com.project.finances.domain.usecases.user;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserCode;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.CryptographyProtocol;
import com.project.finances.domain.protocols.UserAccountProtocol;
import com.project.finances.domain.usecases.user.dto.RedefinePasswordDto;
import com.project.finances.domain.usecases.user.dto.UserCreateDto;
import com.project.finances.domain.usecases.user.dto.UserUpdateDto;
import com.project.finances.domain.usecases.user.email.MailCreateAccountProtocol;
import com.project.finances.domain.usecases.user.email.MailRetrievePasswordProtocol;
import com.project.finances.domain.usecases.user.repository.UserCodeCommand;
import com.project.finances.domain.usecases.user.repository.UserCodeQuery;
import com.project.finances.domain.usecases.user.repository.UserCommand;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.project.finances.domain.exception.messages.MessagesException.*;

@Service
@RequiredArgsConstructor
public class UserAccount implements UserAccountProtocol, UserDetailsService {

    private final UserQuery userQuery;
    private final UserCommand userCommand;
    private final CryptographyProtocol cryptographyProtocol;
    private final MailCreateAccountProtocol mailCreateAccountProtocol;
    private final MailRetrievePasswordProtocol mailRetrievePasswordProtocol;
    private final UserCodeCommand userCodeCommand;
    private final UserCodeQuery userCodeQuery;

    @Override
    public User createAccount(UserCreateDto dto) {
        Optional<User> optionalUser = userQuery.findByUsername(dto.getEmail());

        if(optionalUser.isPresent()) throw new BadRequestException(EMAIL_ALREADY_EXISTS);

        String hash = cryptographyProtocol.encodePassword(dto.getPassword());

        User userToSaved = dto.withPassword(dto, hash);

        User userSaved = userCommand.save(userToSaved);

        String code = userCodeCommand.save(userSaved);

        mailCreateAccountProtocol.sendEmailActiveAccount(userSaved, code);

        return userSaved;
    }

    @Override
    public User updateAccount(UserUpdateDto dto) {
        Optional<User> optionalUser = userQuery.findByIdIsActive(dto.getId());

        if(!optionalUser.isPresent()) throw new BadRequestException(USER_NOT_FOUND);

        User userToUpdate = dto.updateAccount(optionalUser.get(), dto);

        return userCommand.save(userToUpdate.withId(optionalUser.get().getId()));
    }

    @Override
    public User detailsAccount(String id) {
        return userQuery.findByIdIsActive(id).orElseThrow(()-> new BadRequestException(USER_NOT_FOUND));
    }

    @Override
    public User activeAccount(String code) {
        UserCode userCode = userCodeQuery.findByCodeToActiveAccount(code).orElseThrow(() -> new BadRequestException(INVALID_CODE_USER));

        User userToUpdate = userCode.getUser().activeAccount().withId(userCode.getUser().getId());

        userCodeCommand.invalidateCode(userCode);

        return userCommand.update(userToUpdate, userCode.getUser().getId());
    }

    @Override
    public User redefinePassword(RedefinePasswordDto dto) {
        UserCode userCode = userCodeQuery.findByCodeToRetrievePassword(dto.getCode()).orElseThrow(()->new BadRequestException("Código inexistente ou inválido"));

        userCodeCommand.invalidateCode(userCode);

        User user = userCode.getUser();

        String newHash = cryptographyProtocol.encodePassword(dto.getPassword());

        User userToUpdate = user.withPassword(newHash);

        return userCommand.update(userToUpdate, user.getId());
    }

    @Override
    public void retrievePassword(String email) {
        Optional<User> optionalUser = userQuery.findByUsername(email);

        if(optionalUser.isPresent()){
            String code = userCodeCommand.save(optionalUser.get());

            mailRetrievePasswordProtocol.sendEmailRetrievePassword(optionalUser.get(), code);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userQuery.findByUsername(email).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_FOUND));
    }
}
