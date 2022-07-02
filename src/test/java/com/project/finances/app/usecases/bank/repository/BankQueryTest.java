package com.project.finances.app.usecases.bank.repository;

import com.project.finances.domain.entity.Bank;
import com.project.finances.mocks.entity.BankMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BankQueryTest {
    @Mock
    private BankRepository repository;

    private BankQuery query;

    @BeforeEach
    void setup(){
        query = new BankQuery(repository);
    }

    @Test
    @DisplayName("Should return a list of banks by user id")
    void getBanks(){
        Bank bankMock = BankMock.get();

        String description = "description-example";
        String userId = "user-id-valid";

        when(repository.findBankByUserIdAndDescription(userId, description)).thenReturn(Collections.singletonList(bankMock));

        List<Bank> result = query.getBanksByUser(userId, description);

        BDDAssertions.assertThat(result).isNotEmpty().hasSize(1).isEqualTo(Collections.singletonList(bankMock));

        verify(repository, times(1)).findBankByUserIdAndDescription(userId, description);
    }

    @Test
    @DisplayName("Should return a bank by id")
    void getBankById(){
        Bank bankMock = BankMock.get();

        String id = "id-valid";

        when(repository.findById(id)).thenReturn(Optional.of(bankMock));

        Optional<Bank> result = query.getBankById(id);

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(bankMock);

        verify(repository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return a bank by id and by user id")
    void getBankByIdAndByUser(){
        Bank bankMock = BankMock.get();

        String id = "id-valid";

        when(repository.findByIdAndByUserId(id, bankMock.getUser().getId())).thenReturn(Optional.of(bankMock));

        Optional<Bank> result = query.getBankByIdAndByUserId(id, bankMock.getUser().getId());

        BDDAssertions.assertThat(result).isPresent();
        BDDAssertions.assertThat(result.get()).isEqualTo(bankMock);

        verify(repository, times(1)).findByIdAndByUserId(id, bankMock.getUser().getId());
    }
}