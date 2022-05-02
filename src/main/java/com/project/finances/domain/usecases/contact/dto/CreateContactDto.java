package com.project.finances.domain.usecases.contact.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateContactDto {

    private UserContactDto userReceive;
    private String username;
    private String avatar;

}
