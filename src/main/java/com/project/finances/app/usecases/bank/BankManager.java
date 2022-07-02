package com.project.finances.app.usecases.bank;

import com.project.finances.app.usecases.bank.dto.BankDto;
import com.project.finances.app.usecases.bank.repository.BankCommand;
import com.project.finances.app.usecases.bank.repository.BankQuery;
import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.domain.entity.Bank;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.finances.domain.exception.messages.MessagesException.*;

@Service
@RequiredArgsConstructor
public class BankManager implements BankManagerProtocol {
    private final BankQuery query;

    private final BankCommand command;
    private final UserQuery userQuery;

    @Override
    public BankDto create(BankDto dto, String userId) {
        User user = userQuery.findByIdIsActive(userId).orElseThrow(()-> new BadRequestException(BANK_USER_NOT_PROVIDER));

        Bank bankToSave = BankDto.of(dto).withUser(user);

        return BankDto.of(command.save(bankToSave));
    }


    @Override
    public List<BankDto> getBanks(String userId, String description) {
        return query.getBanksByUser(userId, description).stream().map(BankDto::of).collect(Collectors.toList());
    }

    @Override
    public BankDto update(BankDto dto, String userId) {
        Bank bankToUpdate = query.getBankByIdAndByUserId(dto.getId(), userId).orElseThrow(()-> new BadRequestException(BANK_NOT_FOUND));

        bankToUpdate.withDescription(dto.getDescription()).withImage(dto.getImage());

        return BankDto.of(command.save(bankToUpdate));
    }

    @Override
    public void delete(String id, String userId) {
        Bank bank = query.getBankByIdAndByUserId(id, userId).orElseThrow(()-> new BadRequestException(BANK_NOT_FOUND));

        bank.disable();

        command.save(bank);
    }
}
