package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.Contact;

import java.util.List;

public interface UserInviteProtocol {

    List<Contact> listInvites(String userReceiveInviteId);

    void acceptInvite(String inviteId, String userReceiveInviteId);

    void refusedInvite(String inviteId, String userReceiveInviteId);

}
