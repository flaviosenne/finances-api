package com.project.finances.app.usecases.bank.repository;

import com.project.finances.domain.entity.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankQuery {
    private final BankRepository repository;

    public List<Bank> getCategoriesByUser(String userId, String description) {
        return repository.findBankByUserIdAndDescription(userId, description);
    }

    public Optional<Bank> getBankById(String id) {
        return repository.findById(id);
    }

    public Optional<Bank> getBankByIdAndByUserId(String id, String userId) {
        return repository.findByIdAndByUserId(id, userId);
    }
}
