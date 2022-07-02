package com.project.finances.app.usecases.bank;

import com.project.finances.app.usecases.bank.dto.BankDto;
import com.project.finances.app.usecases.category.dto.CategoryDto;
import com.project.finances.domain.entity.Bank;

import java.util.List;

public interface BankManagerProtocol {

    BankDto create(BankDto dto, String userId);

    List<BankDto> getBanks(String userId, String description);

    BankDto update(BankDto dto, String userId);

    void delete(String id, String userId);

}
