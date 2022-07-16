package com.project.finances.mocks.dto;

import com.project.finances.app.usecases.release.dto.ReleaseBankDto;

public class ReleaseBankDtoMock {
    public static ReleaseBankDto get(){
        return new ReleaseBankDto(BankDtoMock.get().getId());
    }
}
