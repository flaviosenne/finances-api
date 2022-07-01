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
@Table(name = "bank")
@EqualsAndHashCode
public class Bank extends BasicEntity{

    private String image;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Bank withUser(User user){
        return Bank.builder()
                .description(this.description)
                .user(user)
                .build();
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
}
