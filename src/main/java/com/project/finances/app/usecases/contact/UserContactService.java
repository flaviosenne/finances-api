package com.project.finances.app.usecases.contact;

import com.project.finances.app.usecases.contact.repository.ContactInviteQuery;
import com.project.finances.app.usecases.contact.repository.UserContactCommand;
import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.usecases.UserContactProtocol;
import com.project.finances.app.usecases.contact.dto.MakeUserPublicDto;
import com.project.finances.app.usecases.contact.repository.UserContactQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.finances.domain.exception.messages.MessagesException.*;


@Service
@RequiredArgsConstructor
public class UserContactService implements UserContactProtocol {

    private final UserContactCommand userContactCommand;
    private final UserContactQuery userContactQuery;
    private final ContactInviteQuery contactInviteQuery;
    private final UserQuery userQuery;

    @Override
    public void makePublic(String userId, MakeUserPublicDto dto) {
        User user = userQuery.findByIdIsActive(userId).orElseThrow(()-> new BadRequestException(USER_NOT_FOUND));

        if(userContactQuery.getUserContact(user.getId()).isPresent()) throw  new BadRequestException(USER_ALREADY_PUBLIC);

        UserContact userContactToSave = MakeUserPublicDto.create(dto, user);

        userContactCommand.makeUserPublic(userContactToSave);
    }

    @Override
    public List<UserContact> listContacts(String userId) {
        UserContact usercontact = userContactQuery.getUserContact(userId)
                .orElseThrow(()-> new BadRequestException(CONTACT_NOT_FOUND));

        return contactInviteQuery.getContacts(usercontact.getId());
    }

    @Override
    public List<UserContact> searchUsers(String username) {
        return userContactQuery.searchUsers(username);
    }
}
