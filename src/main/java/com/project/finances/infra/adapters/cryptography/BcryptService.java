package com.project.finances.infra.adapters.cryptography;

import com.project.finances.domain.exception.ServerErrorException;
import com.project.finances.app.usecases.user.ports.CryptographyProtocol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BcryptService implements CryptographyProtocol {

    @Override
    public String encodePassword(String password) {
        try{
            return new BCryptPasswordEncoder().encode(password);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ServerErrorException("Erro em criptografar a senha");
        }
    }

    @Override
    public boolean passwordMatchers(String password, String hash) {
        try {
            return new BCryptPasswordEncoder().matches(password, hash);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ServerErrorException("Erro em validar senha");
        }
    }
}
