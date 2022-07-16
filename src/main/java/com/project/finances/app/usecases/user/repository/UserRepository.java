package com.project.finances.app.usecases.user.repository;

import com.project.finances.domain.entity.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsActive(String email);

    Optional<User> findByIdAndIsActive(String id);

    Optional<User> findById(String id);

    User save(User  user);

}
