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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BankCommandTest {
    @Mock
    private BankRepository repository;

    private BankCommand command;

    @BeforeEach
    void setup(){
        command = new BankCommand(repository);
    }

    @Test
    @DisplayName("Should save bank when bank is provider")
    void save(){
        Bank bankMock = BankMock.get();

        when(repository.create(any(Bank.class))).thenReturn(bankMock);

        Bank result = command.save(bankMock);

        BDDAssertions.assertThat(result).isEqualTo(bankMock);

        verify(repository, times(1)).create(bankMock);
    }
}