package com.project.finances.app.usecases.user.repository.code;

import com.project.finances.domain.entity.UserCode;

import java.util.Optional;

public interface UserCodeRepository {
    Optional<UserCode> findByUserId(String userId);

    Optional<UserCode> findByCodeRetrievePassword(String code);

    Optional<UserCode> findByCodeActiveAccount(String code);

    UserCode save(UserCode userCode);
}
