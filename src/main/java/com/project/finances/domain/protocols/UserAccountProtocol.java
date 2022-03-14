package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.usecases.user.dto.RedefinePasswordDto;

public interface UserAccountProtocol {

    User createAccount(User user);

    User detailsAccount(String id);

    User activeAccount(String id);

    User redefinePassword(RedefinePasswordDto dto);

    void retrievePassword(String email);
}
