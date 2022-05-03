package com.project.finances.app.vo.invite;

import com.project.finances.app.vo.user.SearchUsersPublicVo;
import com.project.finances.domain.entity.Contact;
import com.project.finances.domain.entity.UserContact;
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

    public  static ListUserInviteVo of(Contact entity){
        return ListUserInviteVo.builder()
                .id(entity.getId())
                .status(entity.getStatus().getDescription())
                .username(entity.getUserRequest().getUsername())
                .avatar(entity.getUserRequest().getAvatar())
                .build();
    }
}
