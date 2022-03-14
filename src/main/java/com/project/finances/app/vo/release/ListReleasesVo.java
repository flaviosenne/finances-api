package com.project.finances.app.vo.release;

import com.project.finances.domain.entity.Category;
import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.Status;
import com.project.finances.domain.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ListReleasesVo {

    private String id;

    private Double value;

    private String description;

    private Status status;

    private Type type;

    private Date createdAt;

    private Date dueDate;

    private CategoryVo category;

    public static ListReleasesVo of(Release release){
        return ListReleasesVo.builder()
                .id(release.getId())
                .value(release.getValue())
                .description(release.getDescription())
                .status(release.getStatus())
                .type(release.getType())
                .createdAt(release.getCreatedAt())
                .dueDate(release.getDueDate())
                .category(CategoryVo.builder().name(release.getCategory().getDescription()).build())
                .build();
    }
}
