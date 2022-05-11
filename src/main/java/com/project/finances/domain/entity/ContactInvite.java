package com.project.finances.domain.entity;

import lombok.*;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@Getter
@Entity
@Table(name = "custom_user_contact_invite")
@EqualsAndHashCode
public class ContactInvite extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private StatusInvite status = StatusInvite.PENDING;

    @OneToOne
    @JoinColumn(name = "user_contact_request_id", referencedColumnName = "id")
    private UserContact userRequest;

    @ManyToOne
    @JoinColumn(name = "user_contact_receive_id", referencedColumnName = "id")
    private UserContact userReceive;

    public ContactInvite withId(String id){
        this.id = id;
        return this;
    }


    public ContactInvite acceptInvite(){
        this.status = StatusInvite.ACCEPT;
        return this;
    }


    public ContactInvite refusedInvite(){
        this.status = StatusInvite.REFUSED;
        return this;
    }
}
