package com.project.finances.mocks.dto;

import com.project.finances.app.usecases.release.dto.ReleaseDto;
import com.project.finances.domain.entity.Release;
import com.project.finances.mocks.entity.ReleaseMock;


public class ReleaseDtoMock {
    public static ReleaseDto get(){
        Release release = ReleaseMock.get();
        return new ReleaseDto(
                release.getId(), release.getDescription(), release.getStatusRelease(),
                release.getTypeRelease(), release.getDueDate(), release.getValue(),
                ReleaseCategoryDtoMock.get());
    }
}
