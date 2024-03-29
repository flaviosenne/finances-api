package com.project.finances.app.usecases.release.dto;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.StatusRelease;
import com.project.finances.domain.entity.TypeRelease;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
public class ReleaseDto {
    private String id;
    private String description;
    private String status;
    private String type;
    private Date dueDate;
    private Double value;
    private ReleaseCategoryDto category;
    private ReleaseBankDto bank;

    public static Release of(ReleaseDto dto){
        return Release.builder()
                .dueDate(dto.getDueDate())
                .description(dto.getDescription())
                .statusRelease(StatusRelease.getStatus(dto.getStatus()).name())
                .typeRelease(TypeRelease.getType(dto.getType()).name())
                .value(dto.getValue())
                .build();
    }

    public static ReleaseDto of(Release entity){
        String bankId = entity.getBank() == null ? null : entity.getBank().getId();
        return ReleaseDto.builder()
                .id(entity.getId())
                .dueDate(entity.getDueDate())
                .description(entity.getDescription())
                .status(entity.getStatusRelease())
                .type(entity.getTypeRelease())
                .value(entity.getValue())
                .category(ReleaseCategoryDto.builder().id(entity.getCategory().getId()).build())
                .bank(ReleaseBankDto.builder().id(bankId).build())
                .build();
    }


}
