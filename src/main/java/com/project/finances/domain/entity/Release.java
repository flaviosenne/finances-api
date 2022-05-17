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

    private String statusRelease = StatusRelease.PENDING.name();

    private String typeRelease = TypeRelease.EXPENSE.name();

    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(columnDefinition = "not null boolean default true")
    private boolean active = true;

    public Release withUser(User user){
        return Release.builder()
                .value(this.value)
                .typeRelease(this.typeRelease)
                .statusRelease(this.statusRelease)
                .description(this.description)
                .dueDate(this.dueDate)
                .category(this.category)
                .active(this.active)
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
                .active(this.active)
                .build();
    }

    public Release withId(String id){
        this.id = id;
        return this;
    }

    public Release active(){
        this.active = true;
        return this;
    }

    public Release disable(){
        this.active = false;
        return this;
    }
}
