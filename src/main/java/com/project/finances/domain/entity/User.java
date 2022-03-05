package com.project.finances.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collection;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "custom_user")
public class User extends BasicEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    public User withPassword(String hash){
        return User.builder()
                .password(hash)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(isActive)
                .build();
    }

    public User activeAccount(){
        return User.builder()
                .password(password)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .isActive(true)
                .build();
    }

    public User withId(String id){
        this.id = id;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
