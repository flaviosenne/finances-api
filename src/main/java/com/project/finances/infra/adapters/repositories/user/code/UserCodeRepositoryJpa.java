package com.project.finances.infra.adapters.repositories.user.code;

import com.project.finances.app.usecases.user.repository.code.UserCodeRepository;
import com.project.finances.domain.entity.UserCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCodeRepositoryJpa implements UserCodeRepository {

    private final UserCodeInterfaceJpa jpa;
    @Override
    public Optional<UserCode> findByUserId(String userId) {
        return jpa.findByUserId(userId);
    }

    @Override
    public Optional<UserCode> findByCodeRetrievePassword(String code) {
        return jpa.findByCodeRetrievePassword(code);
    }

    @Override
    public Optional<UserCode> findByCodeActiveAccount(String code) {
        return jpa.findByCodeActiveAccount(code);
    }

    @Override
    public UserCode save(UserCode userCode) {
        return jpa.save(userCode);
    }
}
