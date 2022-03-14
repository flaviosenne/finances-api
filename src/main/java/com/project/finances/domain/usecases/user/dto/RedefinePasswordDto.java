package com.project.finances.domain.usecases.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RedefinePasswordDto {

    private final String code;

    private final String password;
}
