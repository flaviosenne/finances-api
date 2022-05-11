package com.project.finances.domain.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

interface ReleaseRepository extends JpaRepository<Release, String>, JpaSpecificationExecutor<Release> {
    @Query("select r from Release r join r.user u where u.id = :userId")
    Page<Release> findAllByUser(String userId, Pageable pageable);

    @Query("select r from Release r join r.user u where u.id = :userId and r.id = :id")
    Optional<Release> findByIdAndByUserId(String id, String userId);

    @Query("delete from Release r where r.id = :id and r.user.id = :userId")
    void deleteByIdAndByUserId(String id, String userId);
    @Query("select r from Release r join r.user u where u.id = :userId " +
            "and r.dueDate >= :today and r.dueDate <= :plus5Day ")
    List<Release> findReleasesCloseExpirationIn5Days(String userId, Date today, Date plus5Day);
}
