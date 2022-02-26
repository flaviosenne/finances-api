package com.project.finances.domain.usecases.user.repository;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCommand {
    private final UserRepository repository;

    public User save(User user){
        return repository.save(user);
    }

    public User update(User user, String id){
        user.withId(id);
        return repository.save(user);
    }
}
