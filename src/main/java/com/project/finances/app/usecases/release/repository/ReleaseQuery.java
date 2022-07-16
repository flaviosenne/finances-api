package com.project.finances.app.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReleaseQuery {
    private final ReleaseRepository repository;

    public Page<Release> getReleases(String userId, Pageable pageable){
        return new PageImpl<>(repository.findAllByUserId(userId, pageable));
    }

    public Optional<Release> findReleaseById(String id, String userId){
        return repository.findByIdAndByUserId(id, userId);
    }

    public List<Release> findReleasesCloseExpiration(String userId) {
        Date yesterday = new Date(new Date().getTime() - 86400000);
        return repository.findReleasesCloseExpirationIn5Days(userId, yesterday);
    }

    public Double getBalanceTotal(String userId) {
        return repository.getBalanceTotal(userId);
    }
}
