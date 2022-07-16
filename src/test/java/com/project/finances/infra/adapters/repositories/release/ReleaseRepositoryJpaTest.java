package com.project.finances.infra.adapters.repositories.release;

import com.project.finances.domain.entity.Release;
import com.project.finances.mocks.entity.ReleaseMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ReleaseRepositoryJpaTest {

    @Mock
    private ReleaseInterfaceJpa jpa;

    private ReleaseRepositoryJpa repository;

    @BeforeEach
    void setup(){
        repository = new ReleaseRepositoryJpa(jpa);
    }

    @Test
    @DisplayName("should return pageable of releases by user id")
    void findAllByUser(){
        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();
        Pageable pageMock = mock(Pageable.class);

        when(jpa.findAllByUser(userId, pageMock)).thenReturn(new PageImpl<>(List.of(releaseMock)));

        Page<Release> result = repository.findAllByUser(userId, pageMock);

        BDDAssertions.assertThat(result).isEqualTo(new PageImpl<>(List.of(releaseMock)));
        verify(jpa, times(1)).findAllByUser(userId, pageMock);
    }


    @Test
    @DisplayName("should return optional releases when user id and release id is provider")
    void findByIdAndByUserId(){
        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();

        when(jpa.findByIdAndByUserId(releaseMock.getId(), userId)).thenReturn(Optional.of(releaseMock));

        Optional<Release> result = repository.findByIdAndByUserId(releaseMock.getId(), userId);

        BDDAssertions.assertThat(result).isPresent().isEqualTo(Optional.of(releaseMock));
        verify(jpa, times(1)).findByIdAndByUserId(releaseMock.getId(), userId);
    }


    @Test
    @DisplayName("should return optional releases when user id and release id is provider to delete")
    void findOneReleaseByIdAndByUserIdToDelete(){
        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();

        when(jpa.findOneReleaseByIdAndByUserIdToDelete(releaseMock.getId(), userId)).thenReturn(Optional.of(releaseMock));

        Optional<Release> result = repository.findOneReleaseByIdAndByUserIdToDelete(releaseMock.getId(), userId);

        BDDAssertions.assertThat(result).isPresent().isEqualTo(Optional.of(releaseMock));
        verify(jpa, times(1)).findOneReleaseByIdAndByUserIdToDelete(releaseMock.getId(), userId);
    }


    @Test
    @DisplayName("should return a list releases what is near expiration in 5 days")
    void findReleasesCloseExpirationIn5Days(){
        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();
        Date today = new Date();
        Date plus5Day = new Date(today.getTime() + 518400000);

        when(jpa.findReleasesCloseExpirationIn5Days(userId, today, plus5Day)).thenReturn(List.of(releaseMock));

        List<Release> result = repository.findReleasesCloseExpirationIn5Days(userId, today);

        BDDAssertions.assertThat(result).isEqualTo(List.of(releaseMock));
        verify(jpa, times(1)).findReleasesCloseExpirationIn5Days(userId, today, plus5Day);
    }

    @Test
    @DisplayName("should return a list releases by user id")
    void findAllByUserId(){
        Release releaseMock = ReleaseMock.get();
        String userId = releaseMock.getUser().getId();

        Pageable pageableMock = mock(Pageable.class);

        when(jpa.findAll(any(Specification.class), eq(pageableMock))).thenReturn(new PageImpl(List.of(releaseMock)));

        List<Release> result = repository.findAllByUserId(userId, pageableMock);

        BDDAssertions.assertThat(result).isEqualTo(List.of(releaseMock));
        verify(jpa, times(1)).findAll(any(Specification.class), eq(pageableMock));
    }


    @Test
    @DisplayName("should save release when request is success")
    void save(){
        Release releaseMock = ReleaseMock.get();

        when(jpa.save(releaseMock)).thenReturn(releaseMock);

        Release result = repository.save(releaseMock);

        BDDAssertions.assertThat(result).isEqualTo(releaseMock);

        verify(jpa, times(1)).save(releaseMock);
    }


    @Test
    @DisplayName("should return total balance of release by user id")
    void getBalanceTotal(){
        String userId = ReleaseMock.get().getUser().getId();
        Double totalBalance = 1000d;

        when(jpa.getBalanceTotal(userId)).thenReturn(totalBalance);

        Double result = repository.getBalanceTotal(userId);

        BDDAssertions.assertThat(result).isEqualTo(totalBalance);

        verify(jpa, times(1)).getBalanceTotal(userId);
    }
}