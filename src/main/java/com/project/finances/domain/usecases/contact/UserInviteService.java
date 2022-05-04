package com.project.finances.domain.usecases.contact;

import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.UserInviteProtocol;
import com.project.finances.domain.usecases.contact.dto.CreateContactDto;
import com.project.finances.domain.usecases.contact.repository.ContactInviteCommand;
import com.project.finances.domain.usecases.contact.repository.ContactInviteQuery;
import com.project.finances.domain.usecases.contact.repository.UserContactQuery;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.finances.domain.exception.messages.MessagesException.*;


@Service
@RequiredArgsConstructor
public class UserInviteService implements UserInviteProtocol {

    private final ContactInviteQuery contactInviteQuery;
    private final ContactInviteCommand contactInviteCommand;
    private final UserQuery userQuery;
    private final UserContactQuery userContactQuery;


    @Override
    public List<ContactInvite> listInvites(String userReceiveInviteId) {
        return contactInviteQuery.listInvitesPending(userReceiveInviteId);
    }

    @Override
    public ContactInvite inviteContact(CreateContactDto dto, String userRequestId) {
        User userRequest = userQuery.findByIdIsActive(userRequestId).orElseThrow(()-> new BadRequestException(USER_REQUEST_INVITE_NOT_FOUND));

        UserContact userSendInviteId = userContactQuery.getUserContact(userRequest.getId())
                .orElseThrow(() ->new BadRequestException(CONTACT_NOT_FOUND));

        if(userSendInviteId.getId().equals(dto.getUserContactId())) throw new BadRequestException(USER_CANT_BE_THE_SAME);

        UserContact userReceiveInviteId = userContactQuery.findContactById(dto.getUserContactId())
                .orElseThrow(() ->new BadRequestException(CONTACT_NOT_FOUND));

        if(contactInviteQuery.alreadyExistsInvite(userSendInviteId.getId(), userReceiveInviteId.getId())) throw new BadRequestException(INVITE_ALREADY_EXISTS);

        ContactInvite addContactInvite = CreateContactDto.createContact(userSendInviteId, userReceiveInviteId);

        return contactInviteCommand.inviteUser(addContactInvite);

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
