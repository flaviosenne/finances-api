package com.project.finances.domain.usecases.contact;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.entity.UserContact;
import com.project.finances.domain.exception.BadRequestException;
import com.project.finances.domain.protocols.UserContactProtocol;
import com.project.finances.domain.usecases.contact.dto.MakeUserPublicDto;
import com.project.finances.domain.usecases.contact.repository.ContactInviteQuery;
import com.project.finances.domain.usecases.contact.repository.UserContactCommand;
import com.project.finances.domain.usecases.contact.repository.UserContactQuery;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

}