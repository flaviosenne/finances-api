package com.project.finances.infra.service.email;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class MailServiceTest {

    private JavaMailSender javaMailSender;

    private MailService mailService;

    @BeforeEach
    void setup(){
        MimeMessage mimeMessage = new MimeMessage((Session)null);
        javaMailSender = mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        mailService = new MailService(javaMailSender);
    }

    @Test
    void sendEmail(){
        Assertions.assertDoesNotThrow(()->mailService.sendEmail("content test","subject", new String[]{"test@email.com"}));
    }

    @Test
    void notSendEmail(){
        Throwable exception = BDDAssertions.catchThrowable(()->mailService.sendEmail("content test","subject", null));

        BDDAssertions.assertThat(exception).isInstanceOf(InternalError.class).hasMessage("Falha no envio de email");
    }

}