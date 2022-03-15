package com.project.finances.domain.usecases.release.dto;


import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ReleaseCategoryDto {
    private String id;
}
