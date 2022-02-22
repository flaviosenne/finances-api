package com.project.finances.app.controller;

import com.project.finances.app.dto.LoginDto;
import com.project.finances.app.dto.ResponseLoginDto;
import com.project.finances.domain.protocols.AuthenticationProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationProtocol auth;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDto login(@RequestBody LoginDto dto){
        return auth.login(dto);
    }
}
