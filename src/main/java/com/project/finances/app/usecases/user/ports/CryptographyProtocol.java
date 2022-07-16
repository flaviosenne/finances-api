package com.project.finances.app.usecases.user.ports;

public interface CryptographyProtocol {

    String encodePassword(String password);

    boolean passwordMatchers(String password, String hash);
}
