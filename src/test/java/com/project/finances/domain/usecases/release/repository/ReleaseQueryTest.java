package com.project.finances.domain.usecases.release.repository;


import com.project.finances.domain.entity.*;
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

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReleaseQueryTest {
    @Mock
    private ReleaseRepository repository;

    private ReleaseQuery query;

    @BeforeEach
    void setup(){
        query = new ReleaseQuery(repository);
    }

    @Test
    @DisplayName("Should return a list pageable of releases when userId, specification and page is provider")
    void listReleases(){

        Specification<Release> specificationMock = mock(Specification.class);
        Pageable pageMock = Mockito.mock(Pageable.class);
        String userId = "id-valid";

        User userMock = new User("example@email.com", "first-name", "last-name", "hash", true);
        Category categoryMock = new Category("category 1", userMock);
        Release releaseMock = new Release(100d, "test", Status.PENDING, Type.EXPENSE, new Date(), categoryMock, userMock);

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(new PageImpl<Release>(List.of(releaseMock)));

        Page<Release> result = query.getReleases(userId, specificationMock, pageMock);

        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getContent()).isNotNull().hasSize(1);
        BDDAssertions.assertThat(result.getContent().get(0)).isEqualTo(releaseMock);

        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }


}