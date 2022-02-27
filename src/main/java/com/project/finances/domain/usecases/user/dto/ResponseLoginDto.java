package com.project.finances.domain.usecases.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ResponseLoginDto {
    private final String token;
}
