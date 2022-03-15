package com.project.finances.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "user_code")
public class UserCode extends BasicEntity  {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private boolean isValid;

    public UserCode disableCode(){
        this.isValid = false;
        return this;
    }

    public UserCode withId(String id){
        this.id = id;
        return this;
    }


}
