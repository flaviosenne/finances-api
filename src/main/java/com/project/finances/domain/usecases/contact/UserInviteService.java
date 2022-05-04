package com.project.finances.domain.usecases.contact;

import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.domain.protocols.UserInviteProtocol;
import com.project.finances.domain.usecases.contact.repository.ContactInviteCommand;
import com.project.finances.domain.usecases.contact.repository.ContactInviteQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserInviteService implements UserInviteProtocol {

    private final ContactInviteQuery contactInviteQuery;
    private final ContactInviteCommand contactInviteCommand;

    @Override
    public List<ContactInvite> listInvites(String userReceiveInviteId) {
        return contactInviteQuery.listInvitesPending(userReceiveInviteId);
    }

    @Override
    public void acceptInvite(String inviteId, String userReceiveInviteId) {
        contactInviteCommand.acceptInvite(inviteId, userReceiveInviteId);
    }

    @Override
    public void refusedInvite(String inviteId, String userReceiveInviteId) {
        contactInviteCommand.refusedInvite(inviteId, userReceiveInviteId);
    }

}
