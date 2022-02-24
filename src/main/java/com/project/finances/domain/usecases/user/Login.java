package com.project.finances.domain.usecases.user;

import com.project.finances.app.dto.LoginDto;
import com.project.finances.app.dto.ResponseLoginDto;
import com.project.finances.domain.protocols.AuthenticationProtocol;
import com.project.finances.domain.protocols.CryptographyProtocol;
import com.project.finances.domain.protocols.TokenProtocol;
import com.project.finances.domain.repository.UserQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Login implements AuthenticationProtocol {

    private final UserQuery userQuery;
    private final CryptographyProtocol cryptographyProtocol;
    private final TokenProtocol tokenProtocol;

    @Override
    public ResponseLoginDto login(LoginDto dto) {
        return null;
    }
}
