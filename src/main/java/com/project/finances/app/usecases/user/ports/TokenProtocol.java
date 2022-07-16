package com.project.finances.app.usecases.user.ports;

import com.project.finances.app.usecases.user.auth.dto.ResponseLoginDto;

public interface TokenProtocol {
    ResponseLoginDto generateToken(String id);

    String decodeToken(String token);

    boolean tokenIsValid(String token);
}
