package com.project.finances.domain.usecases.contact;

import com.project.finances.domain.protocols.UserContactProtocol;
import com.project.finances.domain.usecases.contact.repository.ContactInviteCommand;
import com.project.finances.domain.usecases.contact.repository.ContactInviteQuery;
import com.project.finances.domain.usecases.contact.repository.UserContactCommand;
import com.project.finances.domain.usecases.contact.repository.UserContactQuery;
import com.project.finances.domain.usecases.user.repository.UserQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserContactServiceTest {
    @Mock
    UserContactCommand userContactCommand;
    @Mock
    UserContactQuery userContactQuery;
    @Mock
    ContactInviteQuery contactInviteQuery;
    @Mock
    ContactInviteCommand contactInviteCommand;
    @Mock
    UserQuery userQuery;

    UserContactProtocol userContactProtocol;

    @BeforeEach
    void setup(){
        userContactProtocol = new UserContactService(userContactCommand, userContactQuery,
                contactInviteQuery);
    }

    @Test
    void inviteContact(){

    }

}