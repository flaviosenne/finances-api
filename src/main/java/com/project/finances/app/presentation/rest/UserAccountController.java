package com.project.finances.app.presentation.rest;

import com.project.finances.app.presentation.rest.vo.user.CreateUserVo;
import com.project.finances.app.presentation.rest.vo.user.GetUserVo;
import com.project.finances.app.presentation.rest.vo.user.UpdateUserVo;
import com.project.finances.domain.entity.User;
import com.project.finances.app.usecases.user.UserAccountProtocol;
import com.project.finances.app.usecases.user.dto.RedefinePasswordDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserAccountController {

    @Autowired
    private final UserAccountProtocol accountProtocol;

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CreateUserVo dto){
        accountProtocol.createAccount(CreateUserVo.of(dto));
    }

    @CrossOrigin
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UpdateUserVo dto, @AuthenticationPrincipal User user){
        accountProtocol.updateAccount(UpdateUserVo.of(dto, user.getId()));
    }

    @CrossOrigin
    @PostMapping("/retrieve-password")
    @ResponseStatus(HttpStatus.OK)
    public void retrievePassword(@RequestParam("email") String email){
        accountProtocol.retrievePassword(email);
    }

    @CrossOrigin
    @PutMapping("/redefine-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void redefinePassword(@RequestBody RedefinePasswordDto dto){
        accountProtocol.redefinePassword(dto);
    }

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GetUserVo detailsAccount(@AuthenticationPrincipal User user){
        return GetUserVo.of(accountProtocol.detailsAccount(user.getId()));
    }

    @CrossOrigin
    @PutMapping("/active-account/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activeAccount(@PathVariable("code") String code) {
        accountProtocol.activeAccount(code);
    }


}
