package com.project.finances.domain.usecases.contact.repository;

import com.project.finances.domain.entity.Contact;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.usecases.contact.dto.CreateContactDto;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.finances.domain.exception.messages.MessagesException.*;

@Service
@RequiredArgsConstructor
public class ContactCommand {
    private final UserContactQuery userContactQuery;
    private final ContactRepository contactRepository;
    private final UserQuery userQuery;
    private final ContactQuery contactQuery;

    public Contact inviteUser(CreateContactDto dto, String userRequestId){
        User userRequest = userQuery.findByIdIsActive(userRequestId).orElseThrow(()-> new BadRequestException(USER_REQUEST_INVITE_NOT_FOUND));

        UserContact userSendInviteId = userContactQuery.getUserContact(userRequest.getId())
                .orElseThrow(() ->new BadRequestException(CONTACT_NOT_FOUND));

        if(userSendInviteId.getId().equals(dto.getUserContactId())) throw new BadRequestException(USER_CANT_BE_THE_SAME);

        UserContact userReceiveInviteId = userContactQuery.findContactById(dto.getUserContactId())
                .orElseThrow(() ->new BadRequestException(CONTACT_NOT_FOUND));

        if(contactQuery.alreadyExistsInvite(userSendInviteId.getId(), userReceiveInviteId.getId())) throw new BadRequestException(INVITE_ALREADY_EXISTS);

        Contact addContact = CreateContactDto.createContact(userSendInviteId, userReceiveInviteId);

        return contactRepository.save(addContact);

    }

    private Contact getInvite(String inviteId, String userReceiveInviteId){
        UserContact userContact = userContactQuery.getUserContact(userReceiveInviteId)
                .orElseThrow(()-> new BadRequestException(CONTACT_NOT_FOUND));

        return contactQuery.findByIdAndStatusPending(inviteId, userContact.getId())
                .orElseThrow(()-> new BadRequestException(INVITE_NOT_FOUND));

    }

    public Contact acceptInvite(String inviteId, String userReceiveInviteId){

        Contact invite = getInvite(inviteId, userReceiveInviteId);

        return contactRepository.save(invite.acceptInvite());
    }

    public Contact refusedInvite(String inviteId, String userReceiveInviteId){
        Contact invite = getInvite(inviteId, userReceiveInviteId);

        return contactRepository.save(invite.refusedInvite());
    }


}
