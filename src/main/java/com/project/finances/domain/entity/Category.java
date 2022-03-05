package com.project.finances.domain.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "category")
public class Category extends BasicEntity{
    private String description;
}
