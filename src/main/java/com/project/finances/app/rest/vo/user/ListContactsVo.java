package com.project.finances.app.rest.vo.user;

import com.project.finances.domain.entity.UserContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ListContactsVo {
    private String id;
    private String username;
    private String avatar;
    private ListContactsUserVo user;


    public  static ListContactsVo of(UserContact entity){
        return ListContactsVo.builder()
                .id(entity.getId())
                .avatar(entity.getAvatar())
                .username(entity.getUsername())
                .user(ListContactsUserVo.of(entity.getUser()))
                .build();
    }
}
