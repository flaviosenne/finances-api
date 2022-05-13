package com.project.finances.app.usecases.contact.repository;

import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.finances.domain.exception.messages.MessagesException.*;

@Service
@RequiredArgsConstructor
public class ContactInviteCommand {
    private final UserContactQuery userContactQuery;
    private final ContactInviteRepository contactInviteRepository;
    private final ContactInviteQuery contactInviteQuery;

    public ContactInvite inviteUser(ContactInvite contactInvite){
        return contactInviteRepository.save(contactInvite);
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
