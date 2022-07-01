package com.project.finances.app.usecases.bank.dto;

import com.project.finances.domain.entity.Bank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class BankDto {
    private String id;
    private String description;
    private String image;

    public static BankDto of(Bank bank){
        return BankDto.builder()
                .id(bank.getId())
                .description(bank.getDescription())
                .image(bank.getImage())
                .build();
    }

    public static Bank of(BankDto dto){
        return Bank.builder()
                .description(dto.getDescription())
                .image(dto.getImage())
                .build();
    }
}
