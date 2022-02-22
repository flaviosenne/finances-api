package com.project.finances.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "user")
public class User extends BasicEntity{

    private final String email;

    private final String firstName;

    private final String lastName;

    private final String password;
}
