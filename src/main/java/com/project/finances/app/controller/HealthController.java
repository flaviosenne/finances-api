package com.project.finances.app.controller;

import com.project.finances.domain.protocols.AuthenticationProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

    private final AuthenticationProtocol auth;

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String health(){
        return "Server is UP";
    }
}
