package com.project.finances.domain.usecases.user.repository;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserQuery {
    private final UserRepository repository;

    public Optional<User> findById(String id){
        return repository.findById(id);
    }

    public Optional<User> findByUsername(String email){
        return repository.findByEmail(email);
    }
}
