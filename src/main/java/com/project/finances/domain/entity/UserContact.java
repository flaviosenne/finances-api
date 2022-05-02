package com.project.finances.domain.entity;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "custom_contact")
@EqualsAndHashCode
public class UserContact extends BasicEntity {

    @Column(nullable = false, unique = true)
    private String username;

    private String avatar;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public UserContact withId(String id){
        this.id = id;
        return this;
    }
}
