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
    private Date duDate;
    private String description;
    private String status;
    private String type;
    private Double value;
    private CategoryDto category;
    private UserDto user;

    public static Release of(ReleaseDto dto){
        return Release.builder()
                .dueDate(dto.getDuDate())
                .description(dto.getDescription())
                .status(Status.getStatus(dto.getStatus()))
                .type(Type.getType(dto.getType()))
                .value(dto.getValue())
                .build();
    }


}
