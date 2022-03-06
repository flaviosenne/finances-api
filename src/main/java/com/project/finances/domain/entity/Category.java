package com.project.finances.domain.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Category withUser(User user){
        return Category.builder()
                .description(this.description)
                .user(user)
                .build();
    }

    public Category withId(String id){
        this.id = id;
        return  this;
    }
}
