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
        this.user = user;
        return this;
    }

    public Release withCategory(Category category){
        this.category = category;
        return this;
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

    public Release withStatusPaid(){
        this.statusRelease = StatusRelease.PAID.name();
        return this;
    }
}
