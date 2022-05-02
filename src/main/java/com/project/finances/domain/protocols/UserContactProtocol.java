package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.usecases.contact.dto.CreateContactDto;
import com.project.finances.domain.usecases.contact.dto.MakeUserPublicDto;

import java.util.List;

public interface UserContactProtocol {

    UserContact addContact(CreateContactDto dto);

    void makePublic(String userId, MakeUserPublicDto dto);

    List<UserContact> listContacts(String userId);

    List<UserContact> searchUsers(String username);

}
