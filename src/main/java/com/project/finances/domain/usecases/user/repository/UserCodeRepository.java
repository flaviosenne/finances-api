package com.project.finances.domain.usecases.user.repository;

import com.project.finances.domain.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserCodeRepository extends JpaRepository<UserCode, String> {
    @Query("select c from UserCode c join c.user u where u.id = :userId and c.isValid = true")
    Optional<UserCode> findByUserId(String userId);

    @Query("select code from UserCode code " +
            "join code.user user " +
            "where code.code = :code and user.isActive = true and code.isValid = true")
    Optional<UserCode> findByCodeRetrievePassword(String code);

    @Query("select code from UserCode code " +
            "join code.user user " +
            "where code.code = :code and user.isActive = false and code.isValid = true")
    Optional<UserCode> findByCodeActiveAccount(String code);
}
