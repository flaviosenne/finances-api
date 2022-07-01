package com.project.finances.app.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReleaseRepository {
    Page<Release> findAllByUser(String userId, Pageable pageable);

    Optional<Release> findByIdAndByUserId(String id, String userId);

    Optional<Release> findOneReleaseByIdAndByUserIdToDelete(String id, String userId);

    List<Release> findReleasesCloseExpirationIn5Days(String userId, Date today, Date plus5Day);

    List<Release> findAllByUserId(String userId, Pageable pageable);

    Release save(Release release);
}
