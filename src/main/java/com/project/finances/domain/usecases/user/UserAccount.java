package com.project.finances.domain.usecases.user;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.CryptographyProtocol;
import com.project.finances.domain.protocols.UserAccountProtocol;
import com.project.finances.domain.usecases.user.email.MailCreateAccountProtocol;
import com.project.finances.domain.usecases.user.repository.UserCodeCommand;
import com.project.finances.domain.usecases.user.repository.UserCommand;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.project.finances.domain.exception.messages.MessagesException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserAccount implements UserAccountProtocol, UserDetailsService {

    private final UserQuery userQuery;
    private final UserCommand userCommand;
    private final CryptographyProtocol cryptographyProtocol;
    private final MailCreateAccountProtocol mailCreateAccountProtocol;
    private final UserCodeCommand userCodeCommand;

    @Override
    public User createAccount(User user) {
        Optional<User> optionalUser = userQuery.findByUsername(user.getEmail());

        if(optionalUser.isPresent()) throw new BadRequestException("Email já cadastrado na base de dados");

        String hash = cryptographyProtocol.encodePassword(user.getPassword());

        user.withPassword(hash);

        User userSaved = userCommand.save(user);

        String code = userCodeCommand.save(userSaved);

        mailCreateAccountProtocol.sendEmail(userSaved, code);

        return userSaved;
    }

    @Override
    public User activeAccount(String id) {
        User user = userQuery.findById(id).orElseThrow(() -> new BadRequestException("Código do usuário inválido"));

        User userToUpdate = user.activeAccount();
        userToUpdate.withId(id);

        System.out.println("user: "+ userToUpdate.toString());
        return userCommand.update(userToUpdate, user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userQuery.findByUsername(email).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_FOUND));
    }
}
