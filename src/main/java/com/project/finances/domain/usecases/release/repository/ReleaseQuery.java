package com.project.finances.domain.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static com.project.finances.domain.usecases.release.repository.ReleaseSpecification.listReleaseByUserId;

@Service
@RequiredArgsConstructor
public class ReleaseQuery {
    private final ReleaseRepository repository;

    public Page<Release> getReleases(String userId, Specification specification, Pageable pageable){
        return repository.findAll(listReleaseByUserId(userId), pageable);
    }

    public Optional<Release> findReleaseById(String id, String userId){
        return repository.findByIdAndByUserId(id, userId);
    }
}
