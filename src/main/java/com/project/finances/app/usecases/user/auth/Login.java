package com.project.finances.app.usecases.user.auth;

import com.project.finances.app.usecases.user.ports.CryptographyProtocol;
import com.project.finances.app.usecases.user.auth.dto.LoginDto;
import com.project.finances.app.usecases.user.auth.dto.ResponseLoginDto;
import com.project.finances.app.usecases.user.ports.AuthenticationProtocol;
import com.project.finances.app.usecases.user.ports.TokenProtocol;
import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.project.finances.domain.exception.messages.MessagesException.INVALID_CREDENTIALS;

@Service
@AllArgsConstructor
public class Login implements AuthenticationProtocol {

    private final UserQuery userQuery;
    private final CryptographyProtocol cryptographyProtocol;
    private final TokenProtocol tokenProtocol;

    @Override
    public ResponseLoginDto login(LoginDto dto) {
        Optional<User> optionalUser = userQuery.findByEmailActive(dto.getEmail());

        if(!optionalUser.isPresent()) throw new BadCredentialsException(INVALID_CREDENTIALS);

        boolean isMatcher = cryptographyProtocol.passwordMatchers(dto.getPassword(), optionalUser.get().getPassword());

        if(!isMatcher) throw new BadCredentialsException(INVALID_CREDENTIALS);

        return tokenProtocol.generateToken(optionalUser.get().getId());
    }
}
