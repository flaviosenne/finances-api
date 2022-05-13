package com.project.finances.app.presentation.rest.vo.user;

import com.project.finances.domain.entity.UserContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class SearchUsersPublicVo {
    private String id;
    private String username;
    private String avatar;

    public  static SearchUsersPublicVo of(UserContact entity){
        return SearchUsersPublicVo.builder()
                .id(entity.getId())
                .avatar(entity.getAvatar())
                .username(entity.getUsername())
                .build();
    }
}
