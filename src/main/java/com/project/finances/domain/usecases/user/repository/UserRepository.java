package com.project.finances.domain.usecases.user.repository;

import com.project.finances.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

}
