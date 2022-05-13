package com.project.finances.domain.usecases.contact;

import com.project.finances.app.usecases.contact.UserContactService;
import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.usecases.UserContactProtocol;
import com.project.finances.app.usecases.contact.dto.MakeUserPublicDto;
import com.project.finances.app.usecases.contact.repository.ContactInviteQuery;
import com.project.finances.app.usecases.contact.repository.UserContactCommand;
import com.project.finances.app.usecases.contact.repository.UserContactQuery;
import com.project.finances.app.usecases.user.repository.UserQuery;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserContactServiceTest {
    @Mock
    UserContactCommand userContactCommand;
    @Mock
    UserContactQuery userContactQuery;
    @Mock
    ContactInviteQuery contactInviteQuery;
    @Mock
    UserQuery userQuery;

    UserContactProtocol userContactProtocol;

    @BeforeEach
    void setup(){
        userContactProtocol = new UserContactService(userContactCommand, userContactQuery,
                contactInviteQuery, userQuery);
    }

    // todo make public
    @Test
    @DisplayName("Should throw bad request exception when user not found in DB")
    void userNotFound(){
        MakeUserPublicDto dto = MakeUserPublicDto.builder()
                .username("username")
                .avatar("buffer image")
                .build();

        String userId = "invalid-id";

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->userContactProtocol.makePublic(userId, dto));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class)
                        .hasMessage("Usuário não encontrado");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, never()).getUserContact(userId);
        verify(userContactCommand, never()).makeUserPublic(any(UserContact.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when user already make public")
    void userAlreadyPublic(){
        MakeUserPublicDto dto = MakeUserPublicDto.builder()
                .username("username")
                .avatar("buffer image")
                .build();

        String userId = "valid-id";

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(User.builder().build().withId(userId)));
        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.of(UserContact.builder().build()));

        Throwable exception = BDDAssertions.catchThrowable(()->userContactProtocol.makePublic(userId, dto));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("Usuário já está com a visibilidade para o publico");

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(userContactCommand, never()).makeUserPublic(any(UserContact.class));
    }

    @Test
    @DisplayName("Should make user public")
    void makePublic(){
        MakeUserPublicDto dto = MakeUserPublicDto.builder()
                .username("username")
                .avatar("buffer image")
                .build();

        String userId = "valid-id";

        when(userQuery.findByIdIsActive(userId)).thenReturn(Optional.of(User.builder().build().withId(userId)));
        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.empty());

        userContactProtocol.makePublic(userId, dto);

        verify(userQuery, times(1)).findByIdIsActive(userId);
        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(userContactCommand, times(1)).makeUserPublic(any(UserContact.class));
    }

    @Test
    @DisplayName("Should throw bad request exception when user is not a contact")
    void userContactNotFound(){
        String userId = "valid-id";

        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.empty());

        Throwable exception = BDDAssertions.catchThrowable(()->userContactProtocol.listContacts(userId));

        BDDAssertions.assertThat(exception).isInstanceOf(BadRequestException.class)
                .hasMessage("Username não encontrado / visibilidade privada");

        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(contactInviteQuery, never()).getContacts(anyString());
    }

    @Test
    @DisplayName("Should return a list of contacts")
    void getContacts(){
        String userId = "valid-id";

        when(userContactQuery.getUserContact(userId)).thenReturn(Optional.of(UserContact.builder().build()));
        when(contactInviteQuery.getContacts(anyString())).thenReturn(Arrays.asList(UserContact.builder().build()));

        List<UserContact> result = userContactProtocol.listContacts(userId);

        BDDAssertions.assertThat(result).isNotEmpty();

        verify(userContactQuery, times(1)).getUserContact(userId);
        verify(contactInviteQuery, times(1)).getContacts(anyString());
    }

    // todo search contact
    @Test
    @DisplayName("Should return a list of contacts with usernames")
    void searchUsers(){
        String username = "username";

        when(userContactQuery.searchUsers(username)).thenReturn(Arrays.asList(UserContact.builder().build()));

        List<UserContact> result = userContactProtocol.searchUsers(username);

        BDDAssertions.assertThat(result).isNotEmpty().hasSize(1);

        verify(userContactQuery, times(1)).searchUsers(username);
    }

}