package com.project.finances.domain.protocols.usecases;

import com.project.finances.domain.entity.UserContact;
import com.project.finances.app.usecases.contact.dto.MakeUserPublicDto;

import java.util.List;

public interface UserContactProtocol {


    void makePublic(String userId, MakeUserPublicDto dto);

    List<UserContact> listContacts(String userId);

    List<UserContact> searchUsers(String username);

}
