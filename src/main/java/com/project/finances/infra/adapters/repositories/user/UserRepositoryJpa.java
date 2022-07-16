package com.project.finances.infra.adapters.repositories.user;

import com.project.finances.app.usecases.user.repository.UserRepository;
import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRepositoryJpa implements UserRepository {

    private final UserInterfaceJpa jpa;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpa.findByEmail(email);
    }

    @Override
    public Optional<User> findByEmailAndIsActive(String email) {
        return jpa.findByEmailAndIsActive(email);
    }

    @Override
    public Optional<User> findByIdAndIsActive(String id) {
        return jpa.findByIdAndIsActive(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return jpa.findById(id);
    }

    @Override
    public User save(User user) {
        return jpa.save(user);
    }
}
