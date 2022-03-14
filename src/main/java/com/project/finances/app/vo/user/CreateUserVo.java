package com.project.finances.app.vo.user;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CreateUserVo {
    private String email;

    private String firstName;

    private String lastName;

    private String password;

    public static User of(CreateUserVo dto){
        return User.builder()
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .build();
    }

    public CreateUserVo withPasswordHashed(String hash){
        this.password = hash;
        return this;
    }
}
