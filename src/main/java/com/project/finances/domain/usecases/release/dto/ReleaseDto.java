package com.project.finances.domain.usecases.release.dto;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.Status;
import com.project.finances.domain.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
public class ReleaseDto {
    private String id;
    private Date dueDate;
    private String description;
    private String status;
    private String type;
    private Double value;
    private CategoryDto category;
    private UserDto user;

    public static Release of(ReleaseDto dto){
        return Release.builder()
                .dueDate(dto.getDueDate())
                .description(dto.getDescription())
                .status(Status.getStatus(dto.getStatus()))
                .type(Type.getType(dto.getType()))
                .value(dto.getValue())
                .build();
    }

    public static ReleaseDto of(Release entity){
        return ReleaseDto.builder()
                .dueDate(entity.getDueDate())
                .description(entity.getDescription())
                .status(entity.getStatus().name())
                .type(entity.getType().name())
                .value(entity.getValue())
                .category(CategoryDto.builder().id(entity.getCategory().getId()).build())
                .user(UserDto.builder().id(entity.getUser().getId()).build())
                .build();
    }


}
