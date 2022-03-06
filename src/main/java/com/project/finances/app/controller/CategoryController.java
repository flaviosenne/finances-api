package com.project.finances.app.controller;

import com.project.finances.domain.protocols.CategoryManagerProtocol;
import com.project.finances.domain.usecases.category.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryManagerProtocol categoryManagerProtocol;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryDto dto, @RequestParam("user-id") String userId){
        categoryManagerProtocol.create(dto, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@RequestParam("user-id") String userId){
        return categoryManagerProtocol.getCategories(userId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody CategoryDto dto, @RequestParam("user-id") String userId){
        categoryManagerProtocol.update(dto, userId);
    }
}
