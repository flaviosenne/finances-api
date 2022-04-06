package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.usecases.user.dto.RedefinePasswordDto;
import com.project.finances.domain.usecases.user.dto.UserUpdateDto;

public interface UserAccountProtocol {

    User createAccount(User user);

    User updateAccount(UserUpdateDto dto);

    User detailsAccount(String id);

    User activeAccount(String id);

    User redefinePassword(RedefinePasswordDto dto);

    void retrievePassword(String email);
}
