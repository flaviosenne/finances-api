package com.project.finances.app.usecases.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
public class ResponseLoginDto {
    private final String token;

    private final Date expireIn;
}
