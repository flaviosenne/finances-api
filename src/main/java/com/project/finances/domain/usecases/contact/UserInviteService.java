package com.project.finances.domain.usecases.contact;

import com.project.finances.domain.entity.Contact;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.UserContactProtocol;
import com.project.finances.domain.protocols.UserInviteProtocol;
import com.project.finances.domain.usecases.contact.dto.CreateContactDto;
import com.project.finances.domain.usecases.contact.dto.MakeUserPublicDto;
import com.project.finances.domain.usecases.contact.repository.ContactCommand;
import com.project.finances.domain.usecases.contact.repository.ContactQuery;
import com.project.finances.domain.usecases.contact.repository.UserContactCommand;
import com.project.finances.domain.usecases.contact.repository.UserContactQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.finances.domain.exception.messages.MessagesException.CONTACT_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class UserInviteService implements UserInviteProtocol {

    private final ContactQuery contactQuery;
    private final ContactCommand contactCommand;

    @Override
    public List<Contact> listInvites(String userReceiveInviteId) {
        return contactQuery.listInvitesPending(userReceiveInviteId);
    }

    @Override
    public void acceptInvite(String inviteId, String userReceiveInviteId) {
        contactCommand.acceptInvite(inviteId, userReceiveInviteId);
    }

    @Override
    public void refusedInvite(String inviteId, String userReceiveInviteId) {
        contactCommand.refusedInvite(inviteId, userReceiveInviteId);
    }

}
