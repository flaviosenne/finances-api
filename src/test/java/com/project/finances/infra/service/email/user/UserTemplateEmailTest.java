package com.project.finances.infra.service.email.user;

import com.project.finances.domain.entity.User;
import com.project.finances.infra.service.email.MailService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.TemplateEngine;


@SpringBootTest
@ActiveProfiles("test")
class UserTemplateEmailTest {

    private final TemplateEngine templateEngine = new TemplateEngine();
    private final MailService mailService = new MailService(new JavaMailSenderImpl());

    private UserTemplateEmail userTemplateEmail;

    @BeforeEach
    void setup(){
        userTemplateEmail = new UserTemplateEmail(templateEngine, mailService);
    }

    @Test
    void sendEmailToActiveAccount(){
        User userMock = User.builder().firstName("joao").lastName("senne").email("joao@email.com").isActive(false).build();

        Assertions.assertDoesNotThrow(()->userTemplateEmail.sendEmail(userMock));
    }
    @Test
    void sendEmailToRecoverPassword(){
        User userMock = User.builder().firstName("joao").lastName("senne").email("joao@email.com").isActive(true).build();
        String code = RandomString.hashOf(10);

        Assertions.assertDoesNotThrow(()->userTemplateEmail.sendEmail(userMock, code));
    }

}