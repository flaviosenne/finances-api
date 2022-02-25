package com.project.finances.domain.usecases.user.repository;

import com.project.finances.domain.entity.UserCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserCodeRepository extends JpaRepository<UserCode, String> {
    @Query("select c from UserCode c join c.user u where u.id = :userId and c.isValid = true")
    Optional<UserCode> findByUserId(String userId);
}
