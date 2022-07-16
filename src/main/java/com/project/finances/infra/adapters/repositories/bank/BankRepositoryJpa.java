package com.project.finances.infra.adapters.repositories.bank;

import com.project.finances.app.usecases.bank.repository.BankRepository;
import com.project.finances.domain.entity.Bank;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class BankRepositoryJpa implements BankRepository {

    private final BankInterfaceJpa jpa;

    @Override
    public List<Bank> findBankByUserIdAndDescription(String userId, String description) {
        return jpa.findBankByUserId(userId, description);
    }

    @Override
    public Optional<Bank> findById(String id) {
        return jpa.findById(id);
    }

    @Override
    public Optional<Bank> findByIdAndByUserId(String id, String userId) {
        return jpa.findByIdAndByUserId(id, userId);
    }

    @Override
    public Bank create(Bank bank) {
        return jpa.save(bank);
    }
}
