package com.project.finances.app.controller;

import com.project.finances.domain.protocols.FlowCashProtocol;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/releases")
@RequiredArgsConstructor
public class FlowCashController {

    private final FlowCashProtocol flowCashProtocol;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ReleaseDto dto){
        flowCashProtocol.createRelease(dto);
    }
}
