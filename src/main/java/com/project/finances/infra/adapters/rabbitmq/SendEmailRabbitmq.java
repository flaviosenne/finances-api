package com.project.finances.infra.adapters.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.finances.app.usecases.user.ports.MailCreateAccountProtocol;
import com.project.finances.app.usecases.user.ports.MailRetrievePasswordProtocol;
import com.project.finances.domain.entity.User;
import com.project.finances.infra.adapters.rabbitmq.dto.EmailRabbitmqDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import static com.project.finances.infra.adapters.rabbitmq.RoutingKeys.ACTIVATE_ACCOUNT;
import static com.project.finances.infra.adapters.rabbitmq.RoutingKeys.RECOVERY_PASSWORD;

@Primary
@Component("SendEmailRabbitmq")
@AllArgsConstructor
@Slf4j
public class SendEmailRabbitmq implements MailCreateAccountProtocol, MailRetrievePasswordProtocol {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper mapper;

    @Override
    public void sendEmailActiveAccount(User user, String code) {
        try {
            EmailRabbitmqDto dto = EmailRabbitmqDto.builder().user(user).code(code).build();

            String messageJson = this.mapper.writeValueAsString(dto);

            this.rabbitTemplate.convertAndSend(ACTIVATE_ACCOUNT,messageJson);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailRetrievePassword(User user, String code) {
        try {
            EmailRabbitmqDto dto = EmailRabbitmqDto.builder().user(user).code(code).build();

            String messageJson = this.mapper.writeValueAsString(dto);

            this.rabbitTemplate.convertAndSend(RECOVERY_PASSWORD,messageJson);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
