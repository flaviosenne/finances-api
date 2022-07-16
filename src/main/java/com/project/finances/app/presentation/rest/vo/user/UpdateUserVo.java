package com.project.finances.app.presentation.rest.vo.user;

import com.project.finances.app.usecases.user.dto.UserUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UpdateUserVo {
    private String email;

    private String firstName;

    private String lastName;


    public static UserUpdateDto of(UpdateUserVo dto, String userId){
        return UserUpdateDto.builder()
                .id(userId)
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }

}
