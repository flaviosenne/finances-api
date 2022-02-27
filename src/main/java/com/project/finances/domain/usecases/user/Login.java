package com.project.finances.domain.usecases.user;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.usecases.user.dto.LoginDto;
import com.project.finances.domain.usecases.user.dto.ResponseLoginDto;
import com.project.finances.domain.protocols.AuthenticationProtocol;
import com.project.finances.domain.protocols.CryptographyProtocol;
import com.project.finances.domain.protocols.TokenProtocol;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class Login implements AuthenticationProtocol {

    private final UserQuery userQuery;
    private final CryptographyProtocol cryptographyProtocol;
    private final TokenProtocol tokenProtocol;

    @Override
    public ResponseLoginDto login(LoginDto dto) {
        Optional<User> optionalUser = userQuery.findByUsername(dto.getEmail());

        if(optionalUser.isEmpty()) throw new BadCredentialsException("Credenciais inválidas");

        boolean isMatcher = cryptographyProtocol.passwordMatchers(dto.getPassword(), optionalUser.get().getPassword());

        if(!isMatcher) throw new BadCredentialsException("Credenciais inválidas");

        String token = tokenProtocol.generateToken(optionalUser.get().getId());

        return ResponseLoginDto.builder().token(token).build();
    }
}
