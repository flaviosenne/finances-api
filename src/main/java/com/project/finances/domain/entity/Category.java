package com.project.finances.domain.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "category")
public class Category extends BasicEntity{
    private String image;

    private String description;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Category withUser(User user){
        this.user = user;
        return this;
    }

    public Category withId(String id){
        this.id = id;
        return  this;
    }

    public Category withDescription(String description){
        this.description = description;
        return  this;
    }

    public Category withImage(String image){
        this.image = image;
        return  this;
    }

    public Category disable(){
        this.isActive = false;
        return  this;
    }
}
