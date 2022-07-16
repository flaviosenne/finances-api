package com.project.finances.app.usecases.user.dto;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User withPassword(UserCreateDto dto, String hash){
        return User.builder()
                .password(hash)
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .isActive(false)
                .build();
    }
}
