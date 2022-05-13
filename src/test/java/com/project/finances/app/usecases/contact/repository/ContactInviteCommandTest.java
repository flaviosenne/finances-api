package com.project.finances.app.usecases.contact.repository;

import com.project.finances.domain.entity.ContactInvite;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ContactInviteCommandTest {
    @Mock
    UserContactQuery userContactQuery;
    @Mock
    ContactInviteRepository contactInviteRepository;
    @Mock
    ContactInviteQuery contactInviteQuery;

    ContactInviteCommand contactInviteCommand;

    @BeforeEach
    void setup(){
        contactInviteCommand = new ContactInviteCommand(userContactQuery, contactInviteRepository, contactInviteQuery);
    }

    @Test
    @DisplayName("Should save invite of user request")
    void inviteUser(){
        ContactInvite contactInvite = ContactInvite.builder().build();
        when(contactInviteRepository.save(any())).thenReturn(contactInvite);

        ContactInvite result = contactInviteCommand.inviteUser(contactInvite);

        BDDAssertions.assertThat(result).isNotNull();

        verify(contactInviteRepository, times(1)).save(contactInvite);
    }

}