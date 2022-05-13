package com.project.finances.app.usecases.contact.dto;

import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.domain.entity.StatusInvite;
import com.project.finances.domain.entity.UserContact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateContactDto {
    private String userContactId;

    public static ContactInvite createContact(UserContact userContact, UserContact userContactReceive){
        return ContactInvite.builder()
                .userRequest(userContact)
                .userReceive(userContactReceive)
                .status(StatusInvite.PENDING)
                .build();
    }
}
