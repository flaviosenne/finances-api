package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.User;

public interface UserAccountProtocol {

    User createAccount(User user);

    User activeAccount(String id);
}
