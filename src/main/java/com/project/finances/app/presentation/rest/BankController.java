package com.project.finances.app.presentation.rest;

import com.project.finances.app.usecases.bank.BankManagerProtocol;
import com.project.finances.app.usecases.bank.dto.BankDto;
import com.project.finances.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankManagerProtocol bankManagerProtocol;

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody BankDto dto, @AuthenticationPrincipal User user){
        bankManagerProtocol.create(dto, user.getId());
    }

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BankDto> findAll(@AuthenticationPrincipal User user, @RequestParam(value = "description",defaultValue = "")String description){
        return bankManagerProtocol.getBanks(user.getId(), description);
    }

    @CrossOrigin
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody BankDto dto, @AuthenticationPrincipal User user){
        bankManagerProtocol.update(dto, user.getId());
    }
}
