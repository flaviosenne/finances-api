package com.project.finances.app.usecases.bank.repository;

import com.project.finances.domain.entity.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankCommand {
    private final BankRepository repository;

    public Bank save(Bank bank) {
        return repository.create(bank);
    }
}
