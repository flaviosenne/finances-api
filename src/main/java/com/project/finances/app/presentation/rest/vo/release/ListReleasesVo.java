package com.project.finances.app.presentation.rest.vo.release;

import com.project.finances.domain.entity.Release;
import com.project.finances.domain.entity.StatusRelease;
import com.project.finances.domain.entity.TypeRelease;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
public class ListReleasesVo {

    private String id;

    private Double value;

    private String description;

    private StatusRelease statusRelease;

    private TypeRelease typeRelease;

    private Date createdAt;

    private Date dueDate;

    private CategoryVo category;

    private BankVo bank;

    public static ListReleasesVo of(Release release){
        String bankDescription = release.getBank() == null ? null : release.getBank().getDescription();
        return ListReleasesVo.builder()
                .id(release.getId())
                .value(release.getValue())
                .description(release.getDescription())
                .statusRelease(StatusRelease.getStatus(release.getStatusRelease()))
                .typeRelease(TypeRelease.getType(release.getTypeRelease()))
                .createdAt(release.getCreatedAt())
                .dueDate(release.getDueDate())
                .category(CategoryVo.builder().name(release.getCategory().getDescription()).build())
                .bank(BankVo.builder().name(bankDescription).build())
                .build();
    }
}
