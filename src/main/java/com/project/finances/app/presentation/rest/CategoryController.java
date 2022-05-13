package com.project.finances.app.presentation.rest;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.protocols.usecases.CategoryManagerProtocol;
import com.project.finances.app.usecases.category.dto.CategoryDto;
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
    public List<CategoryDto> getCategories(@AuthenticationPrincipal User user, @RequestParam(value = "description",defaultValue = "")String description){
        return categoryManagerProtocol.getCategories(user.getId(), description);
    }

    @CrossOrigin
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody CategoryDto dto, @AuthenticationPrincipal User user){
        categoryManagerProtocol.update(dto, user.getId());
    }
}
