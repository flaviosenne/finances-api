package com.project.finances.app.dto.user;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserDto {
    private String email;

    private String firstName;

    private String lastName;

    private String password;

    public static User of(UserDto dto){
        return User.builder()
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .build();
    }

    public UserDto withPasswordHashed(String hash){
        this.password = hash;
        return this;
    }
}
