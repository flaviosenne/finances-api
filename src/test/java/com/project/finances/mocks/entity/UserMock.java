package com.project.finances.mocks.entity;

import com.project.finances.domain.entity.User;

public class UserMock {
    public static User get(){
        return new User("example@email.com", "first-name", "last-name", "hash", true);
    }
}
