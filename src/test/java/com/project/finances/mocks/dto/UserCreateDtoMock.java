package com.project.finances.mocks.dto;

import com.project.finances.app.usecases.user.dto.UserCreateDto;
import com.project.finances.domain.entity.User;
import com.project.finances.mocks.entity.UserMock;

public class UserCreateDtoMock {
    public static UserCreateDto get(){
        User userMock = UserMock.get();
        return new UserCreateDto(userMock.getFirstName(),
                userMock.getLastName(), userMock.getEmail(),
                "password");
    }
}
