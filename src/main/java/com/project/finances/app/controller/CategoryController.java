package com.project.finances.app.controller;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.CategoryManagerProtocol;
import com.project.finances.domain.usecases.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryManagerProtocol categoryManagerProtocol;

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryDto dto, @AuthenticationPrincipal User user){
        categoryManagerProtocol.create(dto, user.getId());
    }

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@AuthenticationPrincipal User user){
        return categoryManagerProtocol.getCategories(user.getId());
    }

    @CrossOrigin
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody CategoryDto dto, @AuthenticationPrincipal User user){
        categoryManagerProtocol.update(dto, user.getId());
    }
}
