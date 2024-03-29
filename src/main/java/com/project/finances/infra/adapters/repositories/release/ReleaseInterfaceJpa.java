package com.project.finances.infra.adapters.repositories.release;

import com.project.finances.domain.entity.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

interface ReleaseInterfaceJpa extends JpaRepository<Release, String>, JpaSpecificationExecutor<Release> {
    @Query("select r from Release r join r.user u where u.id = :userId and r.active = true")
    Page<Release> findAllByUser(String userId, Pageable pageable);

    @Query("select r from Release r join r.user u where u.id = :userId and r.id = :id and r.active = true")
    Optional<Release> findByIdAndByUserId(String id, String userId);

    @Query("select r from Release r " +
            "join r.user u " +
            "where r.id = :id " +
            "and u.id = :userId  " +
            "and r.active = true ")
    Optional<Release> findOneReleaseByIdAndByUserIdToDelete(String id, String userId);
    @Query("select r from Release r join r.user u where u.id = :userId " +
            "and r.active = true " +
            "and r.dueDate >= :today and r.dueDate <= :plus5Day ")
    List<Release> findReleasesCloseExpirationIn5Days(String userId, Date today, Date plus5Day);

    @Query(value = "select distinct ( " +
            " (select sum(r.value) " +
            " from custom_release r " +
            " join custom_user u on (r.user_id = u.id)  " +
            " where u.id = :userId  " +
            " and r.active = true " +
            " and r.type_release = 'RECEP') " +
            " - " +
            " (select sum(r.value) " +
            " from custom_release r " +
            " join custom_user u on (r.user_id = u.id)  " +
            " where u.id = :userId  " +
            " and r.active = true " +
            " and r.type_release = 'EXPENSE') " +
            " ) from custom_release", nativeQuery = true)
    Double getBalanceTotal(String userId);
}
