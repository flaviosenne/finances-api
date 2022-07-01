package com.project.finances.infra.adapters.repositories.bank;

import com.project.finances.domain.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface BankInterfaceJpa extends JpaRepository<Bank, String> {
    @Query("select c from Bank c join c.user u " +
            "where u.id = :userId and c.description like %:description% ")
    List<Bank> findBankByUserId(String userId, String description);

    @Query("select c from Bank c join c.user u " +
            "where c.id = :id and u.id = :userId")
    Optional<Bank> findByIdAndByUserId(String id, String userId);
}
