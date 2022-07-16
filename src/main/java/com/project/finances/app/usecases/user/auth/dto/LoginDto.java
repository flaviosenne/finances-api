package com.project.finances.app.usecases.user.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginDto {

    private final String email;

    private final String password;
}
