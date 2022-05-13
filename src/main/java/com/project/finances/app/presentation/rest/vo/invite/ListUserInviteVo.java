package com.project.finances.app.presentation.rest.vo.invite;

import com.project.finances.domain.entity.ContactInvite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ListUserInviteVo {
    private String id;
    private String status;
    private String avatar;
    private String username;

    public  static ListUserInviteVo of(ContactInvite entity){
        return ListUserInviteVo.builder()
                .id(entity.getId())
                .status(entity.getStatus().getDescription())
                .username(entity.getUserRequest().getUsername())
                .avatar(entity.getUserRequest().getAvatar())
                .build();
    }
}
