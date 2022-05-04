package com.project.finances.app.controller;

import com.project.finances.app.vo.invite.ListUserInviteVo;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.UserInviteProtocol;
import com.project.finances.domain.usecases.contact.dto.CreateContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users/invites")
@RequiredArgsConstructor
public class UserInviteController {

    private final UserInviteProtocol inviteProtocol;


    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<ListUserInviteVo> listInvites(@AuthenticationPrincipal User user){
        return inviteProtocol.listInvites(user.getId()).stream().map(ListUserInviteVo::of)
                .collect(Collectors.toList());
    }


    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody CreateContactDto dto, @AuthenticationPrincipal User user){
        inviteProtocol.inviteContact(dto, user.getId());
    }

    @CrossOrigin
    @PatchMapping("/accept/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void acceptInvite(@PathVariable("id") String inviteId, @AuthenticationPrincipal User user){
        inviteProtocol.acceptInvite(inviteId, user.getId());
    }

    @CrossOrigin
    @PatchMapping("/refused/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void refusedInvite(@PathVariable("id") String inviteId, @AuthenticationPrincipal User user){
        inviteProtocol.refusedInvite(inviteId, user.getId());
    }
}
