package com.project.finances.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "custom_release")
@EqualsAndHashCode
public class Release extends BasicEntity {

    private Double value;

    private String description;

    @Enumerated(EnumType.STRING)
    private StatusRelease statusRelease = StatusRelease.PENDING;

    @Enumerated(EnumType.STRING)
    private TypeRelease typeRelease = TypeRelease.EXPENSE;

    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    public Release withUser(User user){
        return Release.builder()
                .value(this.value)
                .typeRelease(this.typeRelease)
                .statusRelease(this.statusRelease)
                .description(this.description)
                .dueDate(this.dueDate)
                .category(this.category)
                .user(user)
                .build();
    }

    public Release withCategory(Category category){
        return Release.builder()
                .value(this.value)
                .typeRelease(this.typeRelease)
                .statusRelease(this.statusRelease)
                .description(this.description)
                .dueDate(this.dueDate)
                .category(category)
                .user(this.user)
                .build();
    }

    public Release withId(String id){
        this.id = id;
        return this;
    }
}
