package com.project.finances.app.usecases.release.repository;


import com.project.finances.domain.entity.*;
import com.project.finances.mocks.entity.ReleaseMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

        Pageable pageMock = Mockito.mock(Pageable.class);
        String userId = "id-valid";
        Release releaseMock = ReleaseMock.get();

        when(repository.findAllByUserId(anyString(),any(Pageable.class))).thenReturn(Collections.singletonList(releaseMock));

        Page<Release> result = query.getReleases(userId, pageMock);

        BDDAssertions.assertThat(result).isNotNull();
        BDDAssertions.assertThat(result.getContent()).isNotNull().hasSize(1);
        BDDAssertions.assertThat(result.getContent().get(0)).isEqualTo(releaseMock);

        verify(repository, times(1)).findAllByUserId(anyString(), any(Pageable.class));
    }


    @Test
    @DisplayName("Should return a optional release when userId is provider")
    void findReleaseById(){

        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();

        when(repository.findByIdAndByUserId(releaseMock.getId(),userId)).thenReturn(Optional.of(releaseMock));

        Optional<Release> result = query.findReleaseById(releaseMock.getId(), userId);

        BDDAssertions.assertThat(result).isNotNull().isPresent().isEqualTo(Optional.of(releaseMock));

        verify(repository, times(1)).findByIdAndByUserId(releaseMock.getId(), userId);
    }


    @Test
    @DisplayName("Should return a optional release when userId is provider")
    void findReleasesCloseExpiration(){

        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();

        when(repository.findReleasesCloseExpirationIn5Days(eq(userId), any(Date.class))).thenReturn(Collections.singletonList(releaseMock));

        List<Release> result = query.findReleasesCloseExpiration(userId);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(Collections.singletonList(releaseMock));

        verify(repository, times(1)).findReleasesCloseExpirationIn5Days(eq(userId), any(Date.class));
    }

    @Test
    @DisplayName("Should return total release balance by user id")
    void getBalanceTotal(){

        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();
        Double total = 1000d;

        when(repository.getBalanceTotal(userId)).thenReturn(total);

        Double result = query.getBalanceTotal(userId);

        BDDAssertions.assertThat(result).isNotNull().isEqualTo(total);

        verify(repository, times(1)).getBalanceTotal(userId);
    }


}