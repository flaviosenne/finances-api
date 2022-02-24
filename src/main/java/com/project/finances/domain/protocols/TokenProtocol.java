package com.project.finances.domain.protocols;

public interface TokenProtocol {
    String generateToken(String id);

    String decodeToken(String token);

    boolean tokenIsValid(String token);
}
