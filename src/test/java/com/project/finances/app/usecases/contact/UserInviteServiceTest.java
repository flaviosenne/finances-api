package com.project.finances.app.usecases.contact;

import com.project.finances.app.usecases.contact.dto.CreateContactDto;
import com.project.finances.app.usecases.contact.repository.ContactInviteCommand;
import com.project.finances.app.usecases.contact.repository.ContactInviteQuery;
import com.project.finances.app.usecases.contact.repository.UserContactQuery;
import com.project.finances.app.usecases.user.repository.UserQuery;
import com.project.finances.domain.entity.ContactInvite;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.usecases.UserInviteProtocol;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserInviteServiceTest {

    @Mock
    ContactInviteQuery contactInviteQuery;
    @Mock
    ContactInviteCommand contactInviteCommand;
    @Mock
    UserQuery userQuery;
    @Mock
    UserContactQuery userContactQuery;

    UserInviteProtocol userInviteProtocol;

    @BeforeEach
    void setup(){
        userInviteProtocol = new UserInviteService(contactInviteQuery, contactInviteCommand,
                userQuery, userContactQuery);
    }

    @Test
    @DisplayName("Should return list invites of user")
    void listInvites(){
        String id = "id-valid";
        when(contactInviteQuery.listInvitesPending(id))
                .thenReturn(Collections.singletonList(ContactInvite.builder().build()));

        List<ContactInvite> result = userInviteProtocol.listInvites(id);

        BDDAssertions.assertThat(result).isNotEmpty().isNotNull();

        verify(contactInviteQuery, times(1)).listInvitesPending(id);
    }

    @Test
    @DisplayName("Should accept invite user when exist invite and not have responded")
    void acceptInvite(){
        String inviteId = "valid-id";
        String userId = "valid-id";

        Assertions.assertDoesNotThrow(()->userInviteProtocol.acceptInvite(inviteId, userId));

        verify(contactInviteCommand, times(1)).acceptInvite(inviteId, userId);
    }


    @Test
    @DisplayName("Should refused invite user when exist invite and not have responded")
    void refusedInvite(){
        String inviteId = "valid-id";
        String userId = "valid-id";

        Assertions.assertDoesNotThrow(()->userInviteProtocol.refusedInvite(inviteId, userId));

        verify(contactInviteCommand, times(1)).refusedInvite(inviteId, userId);
    }

    // todo invite user
    @Test
    @DisplayName("Should throw bad request exception when user don't exists in DB")
    void userNotFound(){
        String userId = "invalid-id";
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.empty());

        CreateContactDto dto = CreateContactDto.builder().build();

        Throwable exception = BDDAssertions.catchThrowable(()->userInviteProtocol.inviteContact(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("Solicitante do convite não existe");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, never()).getUserContact(anyString());
        verify(userContactQuery, never()).findContactById(anyString());
        verify(contactInviteQuery, never()).alreadyExistsInvite(anyString(), anyString());
        verify(contactInviteCommand, never()).inviteUser(any(ContactInvite.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when user don't have visibility public")
    void userIsNotPublic(){
        String userId = "valid-id";
        User user = User.builder().build().withId(userId);
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(user));
        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.empty());

        CreateContactDto dto = CreateContactDto.builder().build();

        Throwable exception = BDDAssertions.catchThrowable(()->userInviteProtocol.inviteContact(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("Username não encontrado / visibilidade privada");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(userContactQuery, never()).findContactById(anyString());
        verify(contactInviteQuery, never()).alreadyExistsInvite(anyString(), anyString());
        verify(contactInviteCommand, never()).inviteUser(any(ContactInvite.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when user try invite same owner")
    void userSendSame(){
        String userId = "valid-id";
        User user = User.builder().build().withId(userId);
        UserContact userContact = UserContact.builder().user(user).build().withId(userId);
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(user));
        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.of(userContact));

        CreateContactDto dto = CreateContactDto.builder().userContactId(userContact.getId()).build();

        Throwable exception = BDDAssertions.catchThrowable(()->userInviteProtocol.inviteContact(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("Você não pode mandar convite para si mesmo");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(userContactQuery, never()).findContactById(anyString());
        verify(contactInviteQuery, never()).alreadyExistsInvite(anyString(), anyString());
        verify(contactInviteCommand, never()).inviteUser(any(ContactInvite.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when user try invite contact with private visibility")
    void userSendNotFound(){
        String userId = "valid-id";
        User user = User.builder().build().withId(userId);
        UserContact userContact = UserContact.builder().user(user).build().withId(userId);
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(user));
        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.of(userContact));
        UserContact userContactReceive = UserContact.builder().user(user).build().withId(userId);
        userContactReceive.withId("other-id");
        when(userContactQuery.findContactById(userContactReceive.getId())).thenReturn(Optional.empty());

        CreateContactDto dto = CreateContactDto.builder().userContactId(userContactReceive.getId()).build();

        Throwable exception = BDDAssertions.catchThrowable(()->userInviteProtocol.inviteContact(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("Username não encontrado / visibilidade privada");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(userContactQuery, times(1)).findContactById(userContactReceive.getId());
        verify(contactInviteQuery, never()).alreadyExistsInvite(anyString(), anyString());
        verify(contactInviteCommand, never()).inviteUser(any(ContactInvite.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when user try invite same owner")
    void alreadyExistInvite(){
        String userId = "valid-id";
        User user = User.builder().build().withId(userId);
        UserContact userContact = UserContact.builder().user(user).build().withId(userId);
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(user));
        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.of(userContact));
        UserContact userContactReceive = UserContact.builder().user(user).build().withId(userId);
        userContactReceive.withId("other-id");
        when(userContactQuery.findContactById(userContactReceive.getId())).thenReturn(Optional.of(userContactReceive));
        when(contactInviteQuery.alreadyExistsInvite(userContact.getId(), userContactReceive.getId())).thenReturn(true);


        CreateContactDto dto = CreateContactDto.builder().userContactId(userContactReceive.getId()).build();

        Throwable exception = BDDAssertions.catchThrowable(()->userInviteProtocol.inviteContact(dto, userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("Você já tem uma solicitação com esse usuário");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(userContactQuery, times(1)).findContactById(userContactReceive.getId());
        verify(contactInviteQuery, times(1)).alreadyExistsInvite(userContact.getId(), userContactReceive.getId());
        verify(contactInviteCommand, never()).inviteUser(any(ContactInvite.class));
    }

    @Test
    @DisplayName("Should invite user when request is successful")
    void inviteUser(){
        String userId = "valid-id";
        User user = User.builder().build().withId(userId);
        UserContact userContact = UserContact.builder().user(user).build().withId(userId);
        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(user));
        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.of(userContact));
        UserContact userContactReceive = UserContact.builder().user(user).build().withId(userId);
        userContactReceive.withId("other-id");
        when(userContactQuery.findContactById(userContactReceive.getId())).thenReturn(Optional.of(userContactReceive));
        when(contactInviteQuery.alreadyExistsInvite(userContact.getId(), userContactReceive.getId())).thenReturn(false);
        when(contactInviteCommand.inviteUser(any())).thenReturn(ContactInvite.builder().build());

        CreateContactDto dto = CreateContactDto.builder().userContactId(userContactReceive.getId()).build();

        ContactInvite result = userInviteProtocol.inviteContact(dto, userId);

        BDDAssertions.assertThat(result).isNotNull();

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(userContactQuery, times(1)).findContactById(userContactReceive.getId());
        verify(contactInviteQuery, times(1)).alreadyExistsInvite(userContact.getId(), userContactReceive.getId());
        verify(contactInviteCommand, times(1)).inviteUser(any(ContactInvite.class));
    }
}