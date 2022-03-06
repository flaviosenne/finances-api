package com.project.finances.domain.protocols;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.usecases.release.dto.ReleaseDto;

public interface FlowCashProtocol {

    ReleaseDto createRelease(ReleaseDto release);
}
