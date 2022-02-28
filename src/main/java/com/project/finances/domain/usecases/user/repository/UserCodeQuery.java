package com.project.finances.domain.usecases.user.repository;

import com.project.finances.domain.entity.UserCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCodeQuery {
    private final UserCodeRepository repository;

    public Optional<UserCode> findByCode(String code){
        return repository.findById(code);
    }
}
