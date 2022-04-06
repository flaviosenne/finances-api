package com.project.finances.domain.usecases.user;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserCode;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.CryptographyProtocol;
import com.project.finances.domain.protocols.UserAccountProtocol;
import com.project.finances.domain.usecases.user.dto.RedefinePasswordDto;
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
    public User createAccount(User user) {
        Optional<User> optionalUser = userQuery.findByUsername(user.getEmail());

        if(optionalUser.isPresent()) throw new BadRequestException(EMAIL_ALREADY_EXISTS);

        String hash = cryptographyProtocol.encodePassword(user.getPassword());

        User userToSaved = user.withPassword(hash);

        User userSaved = userCommand.save(userToSaved);

        mailCreateAccountProtocol.sendEmail(userSaved);

        return userSaved;
    }

    @Override
    public User updateAccount(UserUpdateDto userRequest) {
        Optional<User> optionalUser = userQuery.findByIdIsActive(userRequest.getId());

        if(!optionalUser.isPresent()) throw new BadRequestException(USER_NOT_FOUND);

        User userToUpdate = userRequest.updateAccount(optionalUser.get(), userRequest);

        return userCommand.save(userToUpdate);
    }

    @Override
    public User detailsAccount(String id) {
        return userQuery.findByIdIsActive(id).orElseThrow(()-> new BadRequestException(USER_NOT_FOUND));
    }

    @Override
    public User activeAccount(String id) {
        User user = userQuery.findByIdToActiveAccount(id).orElseThrow(() -> new BadRequestException(INVALID_CODE_USER));

        User userToUpdate = user.activeAccount().withId(user.getId());

        return userCommand.update(userToUpdate, user.getId());
    }

    @Override
    public User redefinePassword(RedefinePasswordDto dto) {
        UserCode userCode = userCodeQuery.findByCode(dto.getCode()).orElseThrow(()->new BadRequestException("Código inexistente ou inválido"));

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

            mailRetrievePasswordProtocol.sendEmail(optionalUser.get(), code);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userQuery.findByUsername(email).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_FOUND));
    }
}
