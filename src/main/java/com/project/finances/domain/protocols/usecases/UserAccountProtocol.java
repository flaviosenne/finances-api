package com.project.finances.domain.protocols.usecases;

import com.project.finances.domain.entity.User;
import com.project.finances.app.usecases.user.dto.RedefinePasswordDto;
import com.project.finances.app.usecases.user.dto.UserCreateDto;
import com.project.finances.app.usecases.user.dto.UserUpdateDto;

public interface UserAccountProtocol {

    User createAccount(UserCreateDto dto);

    User updateAccount(UserUpdateDto dto);

    User detailsAccount(String id);

    User activeAccount(String id);

    User redefinePassword(RedefinePasswordDto dto);

    void retrievePassword(String email);
}
