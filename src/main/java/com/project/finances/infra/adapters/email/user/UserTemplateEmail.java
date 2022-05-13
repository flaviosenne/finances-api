package com.project.finances.infra.adapters.email.user;

import com.project.finances.domain.entity.User;
import com.project.finances.app.usecases.user.email.MailCreateAccountProtocol;
import com.project.finances.app.usecases.user.email.MailRetrievePasswordProtocol;
import com.project.finances.infra.adapters.email.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTemplateEmail implements MailCreateAccountProtocol, MailRetrievePasswordProtocol {
    private final TemplateEngine templateEngine;
    private final MailService mailService;

    @Async
    @Override
    public void sendEmailActiveAccount(User user, String code){

        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("code", code);

        String content =  templateEngine.process("mail/ActiveAccountEmailTemplate", context);

        mailService.sendEmail(content, "Ativação da conta", new String[]{user.getEmail()});
        log.info("Send email success "+ user.getEmail());
    }

    @Async
    @Override
    public void sendEmailRetrievePassword(User user, String code) {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("code", code);

        String content =  templateEngine.process("mail/RetrievePasswordEmailTemplate", context);

        mailService.sendEmail(content, "Recuperação de senha", new String[]{user.getEmail()});
        log.info("Send email success "+ user.getEmail());
    }
}
