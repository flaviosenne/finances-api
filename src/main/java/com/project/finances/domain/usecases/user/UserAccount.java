package com.project.finances.domain.usecases.user;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.CryptographyProtocol;
import com.project.finances.domain.protocols.UserAccountProtocol;
import com.project.finances.domain.usecases.user.repository.UserCommand;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccount implements UserAccountProtocol {

    private final UserQuery userQuery;
    private final UserCommand userCommand;
    private final CryptographyProtocol cryptographyProtocol;

    @Override
    public User createAccount(User user) {
        Optional<User> optionalUser = userQuery.findByUsername(user.getEmail());

        if(optionalUser.isPresent()) throw new BadRequestException("Email já cadastrado na base de dados");

        String hash = cryptographyProtocol.encodePassword(user.getPassword());

        user.withPassword(hash);

        return userCommand.save(user);
    }

    @Override
    public void activeAccount(String id) {
        User user = userQuery.findById(id).orElseThrow(() -> new BadRequestException("Código do usuário inválido"));

        User userToUpdate = user.activeAccount();

        userCommand.update(userToUpdate, user.getId());
    }
}
