package com.project.finances.domain.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "bank")
public class Bank extends BasicEntity{

    private String image;

    private String description;

    @Column(name = "is_active")
    private boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Bank withUser(User user){
        this.user = user;
        return this;
    }

    public Bank withId(String id){
        this.id = id;
        return  this;
    }

    public Bank withDescription(String description){
        this.description = description;
        return  this;
    }

    public Bank withImage(String image){
        this.image = image;
        return  this;
    }

    public Bank disable(){
        this.isActive = false;
        return  this;
    }
}
