package com.project.finances.app.presentation.rest.vo.user;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class GetUserVo {
    private String id;

    private String email;

    private String firstName;

    private String lastName;

    public static GetUserVo of(User user){
        return GetUserVo.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
