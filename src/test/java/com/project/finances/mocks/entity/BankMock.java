package com.project.finances.mocks.entity;

import com.project.finances.domain.entity.Bank;

public class BankMock {
    public static Bank get(){
        return new Bank(null, "bank 1", true, UserMock.get());
    }
}
