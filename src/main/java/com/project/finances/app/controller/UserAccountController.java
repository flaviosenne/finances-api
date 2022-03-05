package com.project.finances.app.controller;

import com.project.finances.app.dto.user.UserDto;
import com.project.finances.domain.protocols.UserAccountProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountProtocol accountProtocol;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDto dto){
        accountProtocol.createAccount(UserDto.of(dto));
    }
}
