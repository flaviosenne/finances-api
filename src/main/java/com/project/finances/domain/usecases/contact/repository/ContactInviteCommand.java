package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.usecases.contact.dto.CreateContactDto;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.finances.domain.exception.messages.MessagesException.*;

@Service
@RequiredArgsConstructor
public class ContactInviteCommand {
    private final UserContactQuery userContactQuery;
    private final ContactInviteRepository contactInviteRepository;
    private final UserQuery userQuery;
    private final ContactInviteQuery contactInviteQuery;

    public ContactInvite inviteUser(CreateContactDto dto, String userRequestId){
        User userRequest = userQuery.findByIdIsActive(userRequestId).orElseThrow(()-> new BadRequestException(USER_REQUEST_INVITE_NOT_FOUND));

        UserContact userSendInviteId = userContactQuery.getUserContact(userRequest.getId())
                .orElseThrow(() ->new BadRequestException(CONTACT_NOT_FOUND));

        if(userSendInviteId.getId().equals(dto.getUserContactId())) throw new BadRequestException(USER_CANT_BE_THE_SAME);

        UserContact userReceiveInviteId = userContactQuery.findContactById(dto.getUserContactId())
                .orElseThrow(() ->new BadRequestException(CONTACT_NOT_FOUND));

        if(contactInviteQuery.alreadyExistsInvite(userSendInviteId.getId(), userReceiveInviteId.getId())) throw new BadRequestException(INVITE_ALREADY_EXISTS);

        ContactInvite addContactInvite = CreateContactDto.createContact(userSendInviteId, userReceiveInviteId);

        return contactInviteRepository.save(addContactInvite);

    }

    private ContactInvite getInvite(String inviteId, String userReceiveInviteId){
        UserContact userContact = userContactQuery.getUserContact(userReceiveInviteId)
                .orElseThrow(()-> new BadRequestException(CONTACT_NOT_FOUND));

        return contactInviteQuery.findByIdAndStatusPending(inviteId, userContact.getId())
                .orElseThrow(()-> new BadRequestException(INVITE_NOT_FOUND));

    }

    public ContactInvite acceptInvite(String inviteId, String userReceiveInviteId){

        ContactInvite invite = getInvite(inviteId, userReceiveInviteId);

        return contactInviteRepository.save(invite.acceptInvite());
    }

    public ContactInvite refusedInvite(String inviteId, String userReceiveInviteId){
        ContactInvite invite = getInvite(inviteId, userReceiveInviteId);

        return contactInviteRepository.save(invite.refusedInvite());
    }


}
