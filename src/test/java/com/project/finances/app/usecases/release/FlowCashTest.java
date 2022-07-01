package com.project.finances.app.usecases.release;

import com.project.finances.domain.entity.*;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.app.usecases.category.repository.CategoryQuery;
import com.project.finances.app.usecases.release.dto.ReleaseCategoryDto;
import com.project.finances.app.usecases.release.dto.ReleaseDto;
import com.project.finances.app.usecases.release.repository.ReleaseCommand;
import com.project.finances.app.usecases.release.repository.ReleaseQuery;
import com.project.finances.app.usecases.user.repository.UserQuery;
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

    // todo save release
    @Test
    @DisplayName("Should save a release when data is provider")
    void saveRelease(){
        String categoryId = "category-id-valid";
        String userId = "user-id-valid";
        User user = getUser();

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(user));
        when(categoryQuery.getCategoryByIdAndByUserId(categoryId, user.getId())).thenReturn(Optional.of(getCategory()));
        when(command.create(any(Release.class))).thenReturn(getRelease());

        ReleaseDto dto = getReleaseDto(categoryId);

        ReleaseDto result = flowCashProtocol.createRelease(dto, userId);

        BDDAssertions.assertThat(result).isNotNull().isInstanceOf(ReleaseDto.class);

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, times(1)).getCategoryByIdAndByUserId(categoryId, user.getId());
        verify(command, times(1)).create(ReleaseDto.of(dto).withCategory(getCategory()).withUser(getUser()).active());
    }

    @Test
    @DisplayName("Should throw bad request exception when try save release and user is not provider or invalid")
    void saveNotFoundUser(){
        String categoryId = "category-id-valid";
        String userId = "user-id-invalid";

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.empty());

        ReleaseDto dto = getReleaseDto(categoryId);

        Throwable exception = BDDAssertions.catchThrowable(()->flowCashProtocol.createRelease(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Usuário não encontrado");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, never()).getCategoryById(anyString());
        verify(command, never()).create(any(Release.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when try save release and  category is not provider or invalid")
    void saveNotFoundCategory(){
        String categoryId = "category-id-invalid";
        String userId = "user-id-valid";
        User user = getUser();

        when(categoryQuery.getCategoryById(categoryId)).thenReturn(Optional.empty());
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(user));

        ReleaseDto dto = getReleaseDto(categoryId);

        Throwable exception = BDDAssertions.catchThrowable(()->flowCashProtocol.createRelease(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Categoria não exitente para esse usuario");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, times(1)).getCategoryByIdAndByUserId(categoryId, user.getId());
        verify(command, never()).create(any(Release.class));
    }

    //todo list releases
    @Test
    @DisplayName("Should return a list pageable of releases when request is successful")
    void list(){
        Specification<Release> specificationMock = mock(Specification.class);
        Pageable pageMock = Mockito.mock(Pageable.class);
        String userId = "id-valid";

        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category(null, "category 1", userMock);
        Release releaseMock = new Release(100d, "test", StatusRelease.PENDING.name(), TypeRelease.EXPENSE.name(), new Date(), categoryMock, userMock, true);

        when(query.getReleases(userId, pageMock)).thenReturn(new PageImpl<Release>(Arrays.asList(releaseMock)));

        Page<Release> result = flowCashProtocol.listReleases(userId, pageMock);


        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getContent()).isNotNull().hasSize(1);
        BDDAssertions.assertThat(result.getContent().get(0)).isEqualTo(releaseMock);

        verify(query, times(1)).getReleases(userId, pageMock);

    }

    // todo update release
    @Test
    @DisplayName("Should update release in DB when exist id and category is valid and is owner user")
    void update(){

        ReleaseDto dto = ReleaseDto.builder().id("valid-id-release")
                .status(StatusRelease.PENDING.name())
                .type(TypeRelease.EXPENSE.name())
                .category(ReleaseCategoryDto.builder().id("valid-id-category").build())
                .build();
        String userId = "valid-id-user";

        when(query.findReleaseById(dto.getId(), userId)).thenReturn(Optional.of(Release.builder()
                .build().withId(dto.getId())));
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(getUser().withId(userId)));
        when(categoryQuery.getCategoryByIdAndByUserId(dto.getCategory().getId(), userId))
                .thenReturn(Optional.of(Category.builder().build().withId(dto.getCategory().getId())));

        when(command.update(any(Release.class), eq(dto.getId()))).thenReturn(Release.builder()
                        .category(Category.builder().build().withId(dto.getCategory().getId()))
                        .typeRelease(TypeRelease.EXPENSE.name()).statusRelease(StatusRelease.PENDING.name()).build().withId(dto.getId()));

        ReleaseDto result = flowCashProtocol.updateRelease(dto,userId);

        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getId()).isNotNull().isEqualTo(dto.getId());
        BDDAssertions.assertThat(result.getCategory().getId()).isNotNull().isEqualTo(dto.getCategory().getId());

        verify(query, times(1)).findReleaseById(dto.getId(), userId);
        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, times(1)).getCategoryByIdAndByUserId(dto.getCategory().getId(),userId);
        verify(command, times(1)).update(any(Release.class),eq(dto.getId()));


    }


    @Test
    @DisplayName("Should throw bad request exception when try update release and user is not provider or invalid")
    void updateNotFoundRelease(){
        String id = "invalid-id-release";
        ReleaseDto dto = ReleaseDto.builder().id(id).build();
        String userId = "user-id-valid";

        when(query.findReleaseById(id, userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(() -> flowCashProtocol.updateRelease(dto, userId));


        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Lançamento não encontrado");

        verify(query, times(1)).findReleaseById(id, userId);
        verify(userQuery, never()).findByIdIsActive(anyString());
        verify(categoryQuery, never()).getCategoryByIdAndByUserId(anyString(), anyString());
        verify(command, never()).create(any(Release.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when try update release and user is not provider or invalid")
    void updateNotFoundUser(){
        String id = "valid-id-release";
        ReleaseDto dto = ReleaseDto.builder().id(id)
                .category(ReleaseCategoryDto.builder().id("valid-id-category").build()).build();
        String userId = "user-id-invalid";

        when(query.findReleaseById(dto.getId(), userId)).thenReturn(Optional.of(Release.builder().build().withId(dto.getId())));
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(() -> flowCashProtocol.updateRelease(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Usuário não encontrado");

        verify(query, times(1)).findReleaseById(id, userId);
        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, never()).getCategoryByIdAndByUserId(anyString(), anyString());
        verify(command, never()).create(any(Release.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when try update release and  category is not provider or invalid")
    void updateNotFoundCategory(){
        String id = "valid-id-release";
        ReleaseDto dto = ReleaseDto.builder().id(id)
                .category(ReleaseCategoryDto.builder().id("invalid-id-category").build()).build();
        String userId = "user-id-valid";

        when(query.findReleaseById(dto.getId(), userId)).thenReturn(Optional.of(Release.builder().build().withId(dto.getId())));
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(getUser().withId(userId)));
        when(categoryQuery.getCategoryByIdAndByUserId(dto.getCategory().getId(), userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(() -> flowCashProtocol.updateRelease(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class).hasMessage("Categoria não exitente para esse usuario");

        verify(query, times(1)).findReleaseById(id, userId);
        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(categoryQuery, times(1)).getCategoryByIdAndByUserId(dto.getCategory().getId(), userId);
        verify(command, never()).create(any(Release.class));
    }


    private User getUser(){
        return new User("example@email.com", "first-name", "last-name", "hash", true);
    }

    private Category getCategory(){
        return  new Category(null, "category 1", getUser());
    }

    private Release getRelease(){
        return new Release(100d, "test", StatusRelease.PENDING.name(), TypeRelease.EXPENSE.name(), new Date(), getCategory(), getUser(), true);
    }

    private ReleaseDto getReleaseDto(String categoryId){
        ReleaseCategoryDto categoryDto = ReleaseCategoryDto.builder().id(categoryId).build();
        return ReleaseDto.builder()
                .category(categoryDto)
                .description(getRelease().getDescription())
                .dueDate(getRelease().getDueDate())
                .status(getRelease().getStatusRelease())
                .type(getRelease().getTypeRelease())
                .value(getRelease().getValue())
                .build();

    }

}