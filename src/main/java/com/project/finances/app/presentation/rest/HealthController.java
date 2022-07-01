package com.project.finances.app.presentation.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthController {

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String health(){
        return "Server is UP";
    }
}
