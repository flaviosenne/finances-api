package com.project.finances.app.controller;

import com.project.finances.app.vo.user.CreateUserVo;
import com.project.finances.app.vo.user.GetUserVo;
import com.project.finances.app.vo.user.UpdateUserVo;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.UserAccountProtocol;
import com.project.finances.domain.protocols.UserContactProtocol;
import com.project.finances.domain.usecases.contact.dto.MakeUserPublicDto;
import com.project.finances.domain.usecases.user.dto.RedefinePasswordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountProtocol accountProtocol;
    private final UserContactProtocol contactProtocol;

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
    @PutMapping("/active-account/{user-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activeAccount(@PathVariable("user-id") String id) {
        accountProtocol.activeAccount(id);
    }



    @CrossOrigin
    @PatchMapping("/make-public")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makePublic(@AuthenticationPrincipal User user, @RequestBody MakeUserPublicDto dto){
        contactProtocol.makePublic(user.getId(), dto);
    }

}
