package com.project.finances.app.vo.user;

import com.project.finances.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ListContactsUserVo {
    private String id;
    private String email;
    private String fistName;
    private String lastName;

    public static ListContactsUserVo of(User entity){
        return ListContactsUserVo.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fistName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }

}
