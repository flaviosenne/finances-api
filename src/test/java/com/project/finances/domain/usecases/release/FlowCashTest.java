package com.project.finances.domain.usecases.release;

import com.project.finances.domain.entity.*;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.FlowCashProtocol;
import com.project.finances.domain.usecases.category.repository.CategoryQuery;
import com.project.finances.domain.usecases.release.dto.ReleaseCategoryDto;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;
import com.project.finances.domain.usecases.release.dto.ReleaseUserDto;
import com.project.finances.domain.usecases.release.repository.ReleaseCommand;
import com.project.finances.domain.usecases.release.repository.ReleaseQuery;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FlowCashTest {
    @Mock
    private ReleaseCommand command;
    @Mock
    private UserQuery userQuery;
    @Mock
    private CategoryQuery categoryQuery;
    @Mock
    private ReleaseQuery query;

    private FlowCashProtocol flowCashProtocol;

    @BeforeEach
    void setup(){
        flowCashProtocol = new FlowCash(command,userQuery, categoryQuery, query);
    }

    @Test
    @DisplayName("Should save a release when data is provider")
    void saveRelease(){
        String categoryId = "category-id-valid";
        String userId = "user-id-valid";

        when(categoryQuery.getCategoryById(categoryId)).thenReturn(Optional.of(getCategory()));
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(getUser()));
        when(command.create(any(Release.class))).thenReturn(getRelease());

        ReleaseDto dto = getReleaseDto(categoryId);

        ReleaseDto result = flowCashProtocol.createRelease(dto, userId);

        BDDAssertions.assertThat(result).isNotNull().isInstanceOf(ReleaseDto.class);

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, times(1)).getCategoryById(categoryId);
        verify(command, times(1)).create(ReleaseDto.of(dto).withCategory(getCategory()).withUser(getUser()));
    }

    @Test
    @DisplayName("Should throw bad request exception when user is not provider or invalid")
    void notFoundUser(){
        String categoryId = "category-id-valid";
        String userId = "user-id-invalid";

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.empty());

        ReleaseDto dto = getReleaseDto(categoryId);

        Throwable exception = BDDAssertions.catchThrowable(()->flowCashProtocol.createRelease(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Usuário não informado");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, never()).getCategoryById(anyString());
        verify(command, never()).create(any(Release.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when category is not provider or invalid")
    void notFoundCategory(){
        String categoryId = "category-id-invalid";
        String userId = "user-id-valid";

        when(categoryQuery.getCategoryById(categoryId)).thenReturn(Optional.empty());
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(getUser()));

        ReleaseDto dto = getReleaseDto(categoryId);

        Throwable exception = BDDAssertions.catchThrowable(()->flowCashProtocol.createRelease(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Categoria não informada");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, times(1)).getCategoryById(categoryId);
        verify(command, never()).create(any(Release.class));
    }

    private ReleaseDto getReleaseDto(String categoryId){
        ReleaseCategoryDto categoryDto = ReleaseCategoryDto.builder().id(categoryId).build();
        return ReleaseDto.builder()
                .category(categoryDto)
                .description(getRelease().getDescription())
                .dueDate(getRelease().getDueDate())
                .status(getRelease().getStatus().name())
                .type(getRelease().getType().name())
                .value(getRelease().getValue())
                .build();

    }

    //todo list releases
    @Test
    @DisplayName("Should return a list pageable of releases when request is successful")
    void list(){
        Specification<Release> specificationMock = mock(Specification.class);
        Pageable pageMock = Mockito.mock(Pageable.class);
        String userId = "id-valid";

        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);
        Release releaseMock = new Release(100d, "test", Status.PENDING, Type.EXPENSE, new Date(), categoryMock, userMock);

        when(query.getReleases(userId, specificationMock, pageMock)).thenReturn(new PageImpl<Release>(Arrays.asList(releaseMock)));

        Page<Release> result = flowCashProtocol.listReleases(userId, specificationMock, pageMock);


        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getContent()).isNotNull().hasSize(1);
        BDDAssertions.assertThat(result.getContent().get(0)).isEqualTo(releaseMock);

        verify(query, times(1)).getReleases(userId, specificationMock, pageMock);

    }

    private User getUser(){
        return new User("example@email.com", "first-name", "last-name", "hash", true);
    }

    private Category getCategory(){
        return  new Category("category 1", getUser());
    }

    private Release getRelease(){
        return new Release(100d, "test", Status.PENDING, Type.EXPENSE, new Date(), getCategory(), getUser());
    }
}