package com.project.finances.infra.service.cryptography;

import com.project.finances.domain.protocols.CryptographyProtocol;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptService implements CryptographyProtocol {

    @Override
    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public boolean passwordMatchers(String password, String hash) {
        return new BCryptPasswordEncoder().matches(password, hash);
    }
}
