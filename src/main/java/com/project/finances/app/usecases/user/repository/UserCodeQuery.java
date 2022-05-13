package com.project.finances.app.usecases.user.repository;

import com.project.finances.domain.entity.UserCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCodeQuery {
    private final UserCodeRepository repository;

    public Optional<UserCode> findByCodeToRetrievePassword(String code){
        return repository.findByCodeRetrievePassword(code);
    }


    public Optional<UserCode> findByCodeToActiveAccount(String code){
        return repository.findByCodeActiveAccount(code);
    }
}
