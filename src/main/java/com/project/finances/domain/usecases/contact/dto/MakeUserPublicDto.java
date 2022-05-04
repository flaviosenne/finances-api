package com.project.finances.domain.usecases.contact.dto;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MakeUserPublicDto {

    private String username;
    private String avatar;

    public static UserContact create(MakeUserPublicDto dto, User user){
        return UserContact.builder()
                .user(user)
                .avatar(dto.getAvatar())
                .username(dto.getUsername())
                .build();
    }

}
