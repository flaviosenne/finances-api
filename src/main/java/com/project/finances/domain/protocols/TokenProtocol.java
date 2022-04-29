package com.project.finances.domain.protocols;

import com.project.finances.domain.usecases.user.dto.ResponseLoginDto;

public interface TokenProtocol {
    ResponseLoginDto generateToken(String id);

    String decodeToken(String token);

    boolean tokenIsValid(String token);
}
