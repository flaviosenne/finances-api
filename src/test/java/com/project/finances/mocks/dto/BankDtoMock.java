package com.project.finances.mocks.dto;

import com.project.finances.app.usecases.bank.dto.BankDto;
import com.project.finances.mocks.entity.BankMock;

public class BankDtoMock {
    public static BankDto get(){
        return new BankDto(BankMock.get().getId(),
                BankMock.get().getDescription(),
                BankMock.get().getImage());
    }
}
