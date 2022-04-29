package com.project.finances.app.vo.user;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.usecases.user.dto.UserCreateDto;
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
