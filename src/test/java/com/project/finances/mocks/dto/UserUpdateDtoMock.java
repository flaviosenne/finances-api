package com.project.finances.mocks.dto;

import com.project.finances.app.usecases.user.dto.UserUpdateDto;
import com.project.finances.domain.entity.User;
import com.project.finances.mocks.entity.UserMock;

public class UserUpdateDtoMock {
    public static UserUpdateDto get(){
        User userMock = UserMock.get();
        return new UserUpdateDto(userMock.getId(),
                userMock.getFirstName(), userMock.getLastName(), userMock.getEmail());
    }
}
