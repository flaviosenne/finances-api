package com.project.finances.domain.protocols.ports;

import com.project.finances.app.usecases.user.dto.ResponseLoginDto;

public interface TokenProtocol {
    ResponseLoginDto generateToken(String id);

    String decodeToken(String token);

    boolean tokenIsValid(String token);
}
