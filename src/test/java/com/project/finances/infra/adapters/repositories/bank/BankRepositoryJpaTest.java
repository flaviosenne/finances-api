package com.project.finances.infra.adapters.repositories.bank;

import com.project.finances.domain.entity.Bank;
import com.project.finances.mocks.entity.BankMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class BankRepositoryJpaTest {

    @Mock
    private BankInterfaceJpa jpa;

    private BankRepositoryJpa repository;


    @BeforeEach
    void setup(){
        repository = new BankRepositoryJpa(jpa);
    }

    @Test
    @DisplayName("should return a bank when user id and description is provider and is match with someone result in DB")
    void findBankByUserIdAndDescription(){
        Bank bankDb = BankMock.get();
        String userId = bankDb.getUser().getId();
        String description = bankDb.getDescription();
        when(jpa.findBankByUserId(userId, description)).thenReturn(List.of(bankDb));

        List<Bank> result = repository.findBankByUserIdAndDescription(userId, description);

        BDDAssertions.assertThat(result).isNotNull().isNotEmpty();
        BDDAssertions.assertThat(result).isEqualTo(List.of(bankDb));

        verify(jpa, times(1)).findBankByUserId(userId, description);
    }

    @Test
    @DisplayName("should return a bank when id is provider and is match with someone result in DB")
    void findById(){
        Bank bankDb = BankMock.get();
        String id = bankDb.getId();
        when(jpa.findById(id)).thenReturn(Optional.of(bankDb));

        Optional<Bank> result = repository.findById(id);

        BDDAssertions.assertThat(result).isNotNull().isPresent();
        BDDAssertions.assertThat(result).isEqualTo(Optional.of(bankDb));

        verify(jpa, times(1)).findById(id);
    }

    @Test
    @DisplayName("should return a bank when id and user id is provider and is match with someone result in DB")
    void findByIdAndByUserId(){
        Bank bankDb = BankMock.get();
        String userId = bankDb.getUser().getId();
        String id = bankDb.getId();
        when(jpa.findByIdAndByUserId(id, userId)).thenReturn(Optional.of(bankDb));

        Optional<Bank> result = repository.findByIdAndByUserId(id, userId);

        BDDAssertions.assertThat(result).isNotNull().isPresent();
        BDDAssertions.assertThat(result).isEqualTo(Optional.of(bankDb));

        verify(jpa, times(1)).findByIdAndByUserId(id, userId);
    }

    @Test
    @DisplayName("should save a bank when this a provider")
    void create(){
        Bank bankDb = BankMock.get();
        when(jpa.save(bankDb)).thenReturn(bankDb);

        Bank result = repository.create(bankDb);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(bankDb);

        verify(jpa, times(1)).save(bankDb);
    }
}