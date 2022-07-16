package com.project.finances.app.usecases.user.repository;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserQuery {
    private final UserRepository repository;

    public Optional<User> findByIdToActiveAccount(String id){
        return repository.findById(id);
    }

    public Optional<User> findByIdIsActive(String id){
        return repository.findByIdAndIsActive(id);
    }

    public Optional<User> findByUsername(String email){
        return repository.findByEmail(email);
    }

    public Optional<User> findByEmailActive(String email){
        return repository.findByEmailAndIsActive(email);
    }

}
