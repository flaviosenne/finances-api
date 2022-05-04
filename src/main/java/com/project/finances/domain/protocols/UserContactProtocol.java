package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.usecases.contact.dto.CreateContactDto;
import com.project.finances.domain.usecases.contact.dto.MakeUserPublicDto;

import java.util.List;

public interface UserContactProtocol {

    ContactInvite addContact(CreateContactDto dto, String userId);

    void makePublic(String userId, MakeUserPublicDto dto);

    List<ContactInvite> listContacts(String userId);

    List<UserContact> searchUsers(String username);

}
