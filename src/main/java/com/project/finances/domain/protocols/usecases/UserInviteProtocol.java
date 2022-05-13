package com.project.finances.domain.protocols.usecases;

import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.app.usecases.contact.dto.CreateContactDto;

import java.util.List;

public interface UserInviteProtocol {

    List<ContactInvite> listInvites(String userReceiveInviteId);

    ContactInvite inviteContact(CreateContactDto dto, String userRequestId);

    void acceptInvite(String inviteId, String userReceiveInviteId);

    void refusedInvite(String inviteId, String userReceiveInviteId);

}
