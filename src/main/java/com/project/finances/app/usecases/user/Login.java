package com.project.finances.app.usecases.user;

import com.project.finances.app.usecases.user.dto.LoginDto;
import com.project.finances.app.usecases.user.dto.ResponseLoginDto;
import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.usecases.AuthenticationProtocol;
import com.project.finances.domain.protocols.ports.CryptographyProtocol;
import com.project.finances.domain.protocols.ports.TokenProtocol;
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
