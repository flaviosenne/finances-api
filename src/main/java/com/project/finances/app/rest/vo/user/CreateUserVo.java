package com.project.finances.app.rest.vo.user;

import com.project.finances.app.usecases.user.dto.UserCreateDto;
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

    public static UserCreateDto of(CreateUserVo dto){
        return UserCreateDto.builder()
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .password(dto.getPassword())
                .build();
    }
}
