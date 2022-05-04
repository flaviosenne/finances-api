package com.project.finances.app.vo.user;

import com.project.finances.domain.entity.ContactInvite;
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


    public  static ListContactsVo of(ContactInvite entity){
        return ListContactsVo.builder()
                .id(entity.getId())
                .avatar(entity.getUserReceive().getAvatar())
                .username(entity.getUserReceive().getUsername())
                .user(ListContactsUserVo.of(entity.getUserReceive().getUser()))
                .build();
    }
}