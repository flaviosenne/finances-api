package com.project.finances.app.usecases.release;

import com.project.finances.domain.entity.Release;
import com.project.finances.app.usecases.release.dto.ReleaseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface FlowCashProtocol {

    ReleaseDto createRelease(ReleaseDto release, String userId);

    void deleteRelease(String id, String userId);

    ReleaseDto updateRelease(ReleaseDto release, String userId);

    ReleaseDto updateStatusRelease(String status, String userId);

    Page<Release> listReleases(String userId, Pageable pageable);

    List<Release> listReleasesReminder(String userId);
}
