package com.project.finances.mocks.dto;

import com.project.finances.app.usecases.release.dto.ReleaseCategoryDto;

public class ReleaseCategoryDtoMock {
    public static ReleaseCategoryDto get(){
        return new ReleaseCategoryDto(CategoryDtoMock.get().getId());
    }
}
