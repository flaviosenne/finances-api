package com.project.finances.infra.adapters.email;

import com.project.finances.app.usecases.user.ports.MailProtocol;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class JavaMailSenderService implements MailProtocol {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String content, String subject,  String[] to) {
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setTo(to);
            helper.setText(content, true);
            helper.setSubject(subject);

            mailSender.send(message);
            log.info("Mail send with success");
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalError("Falha no envio de email");
        }
    }
}
