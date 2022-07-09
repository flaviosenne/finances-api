package com.project.finances.mocks.entity;

import com.project.finances.domain.entity.UserCode;

public class UserCodeMock {
    public static UserCode get(){
        return new UserCode(UserMock.get(), true, "code");
    }
}
