package com.project.finances.domain.protocols;

public interface CryptographyProtocol {

    String encodePassword(String password);

    boolean passwordMatchers(String password, String hash);
}
