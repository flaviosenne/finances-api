package com.project.finances.domain.usecases.contact;

import com.project.finances.domain.entity.Contact;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.UserContactProtocol;
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
public class UserContactService implements UserContactProtocol {

    private final UserContactCommand userContactCommand;
    private final UserContactQuery userContactQuery;
    private final ContactQuery contactQuery;
    private final ContactCommand contactCommand;

    @Override
    public Contact addContact(CreateContactDto dto, String userId) {
        return contactCommand.inviteUser(dto, userId);
    }

    @Override
    public void makePublic(String userId, MakeUserPublicDto dto) {
        userContactCommand.makeUserPublic(userId, dto);
    }

    @Override
    public List<Contact> listContacts(String userId) {
        UserContact usercontact = userContactQuery.getUserContact(userId).orElseThrow(()-> new BadRequestException(CONTACT_NOT_FOUND));

        return contactQuery.getContacts(usercontact.getId());
    }


    @Override
    public List<UserContact> searchUsers(String username) {
        return userContactQuery.searchUsers(username);
    }
}
