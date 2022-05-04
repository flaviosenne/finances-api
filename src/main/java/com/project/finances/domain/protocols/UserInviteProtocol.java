package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.ContactInvite;

import java.util.List;

public interface UserInviteProtocol {

    List<ContactInvite> listInvites(String userReceiveInviteId);

    void acceptInvite(String inviteId, String userReceiveInviteId);

    void refusedInvite(String inviteId, String userReceiveInviteId);

}
