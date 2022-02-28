package com.project.finances.infra.service.email.user;

import com.project.finances.domain.entity.User;
import com.project.finances.domain.usecases.user.email.MailCreateAccountProtocol;
import com.project.finances.domain.usecases.user.email.MailRetrievePasswordProtocol;
import com.project.finances.infra.service.email.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class UserTemplateEmail implements MailCreateAccountProtocol, MailRetrievePasswordProtocol {
    private final TemplateEngine templateEngine;
    private final MailService mailService;

    @Override
    public void sendEmail(User user){

        Context context = new Context();
        context.setVariable("user", user);

        String content =  templateEngine.process("mail/ActiveAccountEmailTemplate", context);

        mailService.sendEmail(content, "Ativação da conta", new String[]{user.getEmail()});
    }

    @Override
    public void sendEmail(User user, String code) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("code", code);

        String content =  templateEngine.process("mail/RetrievePasswordEmailTemplate", context);

        mailService.sendEmail(content, "Recuperação de senha", new String[]{user.getEmail()});
    }
}
