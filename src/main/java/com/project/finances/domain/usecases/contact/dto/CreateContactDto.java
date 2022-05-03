package com.project.finances.domain.usecases.contact.dto;

import com.project.finances.domain.entity.Contact;
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

    public static Contact createContact(UserContact userContact, UserContact userContactReceive){
        return Contact.builder()
                .userRequest(userContact)
                .userReceive(userContactReceive)
                .status(StatusInvite.PENDING)
                .build();
    }
}
