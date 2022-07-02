package com.project.finances.app.usecases.bank;

import com.project.finances.app.usecases.bank.dto.BankDto;
import com.project.finances.app.usecases.bank.repository.BankCommand;
import com.project.finances.app.usecases.bank.repository.BankQuery;
import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.domain.entity.Bank;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.mocks.dto.BankDtoMock;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BankManagerTest {
    @Mock
    private BankQuery query;
    @Mock
    private BankCommand command;
    @Mock
    private UserQuery userQuery;

    private BankManagerProtocol bankManagerProtocol;

    @BeforeEach
    void setup(){
        bankManagerProtocol = new BankManager(query, command, userQuery);
    }

    // todo create
    @Test
    @DisplayName("Should create a bak when request is successful")
    void createBank(){
        Bank bankMock = BankMock.get();
        BankDto dto = BankDtoMock.get();
        String userId = bankMock.getUser().getId();

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(bankMock.getUser()));
        when(command.save(any(Bank.class))).thenReturn(bankMock);

        BankDto result = bankManagerProtocol.create(dto, userId);

        BDDAssertions.assertThat(result).isNotNull();

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(command, times(1)).save(any(Bank.class));

    }

    @Test
    @DisplayName("Should throw bad request exception when user do not exist in Db")
    void notCreateBank(){
        BankDto dto = BankDtoMock.get();
        String userId = "user-id-invalid";

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->bankManagerProtocol.create(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Usuário não informado para banco");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(command, never()).save(any(Bank.class));

    }

    // todo list
    @Test
    @DisplayName("Should return a list banks by user id when request is successful")
    void getBanks(){
        Bank bankMock = BankMock.get();
        String userId = "user-id-valid";
        String description = "example-description";


        when(query.getBanksByUser(userId, description)).thenReturn(Collections.singletonList(bankMock));

        List<BankDto> result = bankManagerProtocol.getBanks(userId,description);

        BDDAssertions.assertThat(result).isNotEmpty().hasSize(1);

        verify(query, times(1)).getBanksByUser(userId, description);

    }

    // todo update
    @Test
    @DisplayName("Should update a bank when request is successful")
    void updateBank(){
        Bank bankMock = BankMock.get();
        BankDto dto = BankDtoMock.get();
        String userId = "user-id-valid";

        when(query.getBankByIdAndByUserId(dto.getId(), userId)).thenReturn(Optional.of(bankMock));
        when(command.save(any(Bank.class))).thenReturn(bankMock);

        BankDto result = bankManagerProtocol.update(dto, userId);

        BDDAssertions.assertThat(result).isNotNull();

        verify(query, times(1)).getBankByIdAndByUserId(dto.getId(),userId);
        verify(command, times(1)).save(any(Bank.class));

    }
    @Test
    @DisplayName("Should throw bad request exception when try update a bank nad not found resource in DB")
    void notUpdateBank(){
        Bank bankMock = BankMock.get();
        BankDto dto = BankDtoMock.get();
        String userId = "user-id-invalid";

        when(query.getBankByIdAndByUserId(bankMock.getId(), userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->bankManagerProtocol.update(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Banco não encontrado");

        verify(query, times(1)).getBankByIdAndByUserId(dto.getId(),userId);
        verify(command, never()).save(any(Bank.class));

    }

    // todo delete
    @Test
    @DisplayName("Should disable a bank when request is successful")
    void deleteBank(){
        Bank bankMock = com.project.finances.mocks.entity.BankMock.get();
        String id = "id-valid";
        String userId = "user-id-valid";

        when(query.getBankByIdAndByUserId(id, userId)).thenReturn(Optional.of(bankMock));
        when(command.save(any(Bank.class))).thenReturn(bankMock);

        bankManagerProtocol.delete(id, userId);

        verify(query, times(1)).getBankByIdAndByUserId(id,userId);
        verify(command, times(1)).save(any(Bank.class));

    }
    @Test
    @DisplayName("Should throw bad request exception when try delete a bank nad not found resource in DB")
    void notDeleteCategory(){
        Bank bankMock = BankMock.get();
        String id = "id-invalid";
        String userId = "user-id-invalid";

        when(query.getBankByIdAndByUserId(bankMock.getId(), userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->bankManagerProtocol.delete(id, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Banco não encontrado");

        verify(query, times(1)).getBankByIdAndByUserId(id,userId);
        verify(command, never()).save(any(Bank.class));

    }


}