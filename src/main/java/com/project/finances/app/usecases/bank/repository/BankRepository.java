package com.project.finances.app.usecases.bank.repository;

import com.project.finances.domain.entity.Bank;

import java.util.List;
import java.util.Optional;

public interface BankRepository {

    List<Bank> findBankByUserIdAndDescription(String userId, String description);

    Optional<Bank> findById(String id);

    Optional<Bank> findByIdAndByUserId(String id, String userId);

    Bank create(Bank bank);
}
