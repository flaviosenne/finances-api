package com.project.finances.app.usecases.user.dto;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserUpdateDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public User updateAccount(User userDB, UserUpdateDto newUser){
        return User.builder()
                .password(userDB.getPassword())
                .email(newUser.getEmail())
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .isActive(userDB.isActive())
                .build();
    }
}
