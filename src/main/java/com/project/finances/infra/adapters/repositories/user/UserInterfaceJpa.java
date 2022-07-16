package com.project.finances.infra.adapters.repositories.user;

import com.project.finances.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInterfaceJpa extends JpaRepository<User, String> {
    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);


    @Query("select u from User u where u.email = :email and u.isActive = true")
    Optional<User> findByEmailAndIsActive(String email);

    @Query("select u from User u where u.id = :id and u.isActive = true")
    Optional<User> findByIdAndIsActive(String id);
}
