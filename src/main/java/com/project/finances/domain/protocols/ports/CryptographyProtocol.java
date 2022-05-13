package com.project.finances.domain.protocols.ports;

public interface CryptographyProtocol {

    String encodePassword(String password);

    boolean passwordMatchers(String password, String hash);
}
