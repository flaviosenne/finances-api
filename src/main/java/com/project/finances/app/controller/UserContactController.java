package com.project.finances.app.controller;

import com.project.finances.app.vo.user.ListContactsVo;
import com.project.finances.app.vo.user.SearchUsersPublicVo;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.UserContactProtocol;
import com.project.finances.domain.usecases.contact.dto.MakeUserPublicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users/contacts")
@RequiredArgsConstructor
public class UserContactController {

    private final UserContactProtocol contactProtocol;


    @CrossOrigin
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.CREATED)
    public List<SearchUsersPublicVo> search(@RequestParam("username") String username){
        return contactProtocol.searchUsers(username).stream().map(SearchUsersPublicVo::of)
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @PatchMapping("/make-public")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makePublic(@AuthenticationPrincipal User user, @RequestBody MakeUserPublicDto dto){
        contactProtocol.makePublic(user.getId(), dto);
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(){
    }


    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ListContactsVo> getContacts(@AuthenticationPrincipal User user){
        return contactProtocol.listContacts(user.getId()).stream().map(ListContactsVo::of)
                .collect(Collectors.toList());

    }

}
