package com.project.finances.app.rest;

import com.project.finances.app.usecases.user.dto.LoginDto;
import com.project.finances.app.usecases.user.dto.ResponseLoginDto;
import com.project.finances.domain.protocols.usecases.AuthenticationProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationProtocol auth;

    @CrossOrigin
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseLoginDto login(@RequestBody LoginDto dto){
        return auth.login(dto);
    }
}
