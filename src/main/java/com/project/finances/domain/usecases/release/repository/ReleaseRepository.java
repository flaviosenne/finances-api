package com.project.finances.domain.usecases.release.repository;

import com.project.finances.domain.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface ReleaseRepository extends JpaRepository<Release, String>, JpaSpecificationExecutor<Release> {
}
