package com.project.finances.infra.adapters.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.project.finances.infra.adapters.rabbitmq.ExchangesMapped.ACTIVATE_ACCOUNT;
import static com.project.finances.infra.adapters.rabbitmq.ExchangesMapped.RECOVERY_PASSWORD;

@Component
@AllArgsConstructor
public class RabbitmqService {
    private final AmqpAdmin amqpAdmin;

    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    private Binding binding(Queue queue, TopicExchange exchange){
        String destination = queue.getName();
        Binding.DestinationType destinationType = Binding.DestinationType.QUEUE;
        String exchangeName = exchange.getName();
        return new Binding(destination, destinationType, exchangeName, "", null);
    }

    @PostConstruct
    public void initializeQueue(){
        Queue emailQueue = this.queue("email");

        TopicExchange activeAccount = new TopicExchange(ACTIVATE_ACCOUNT);
        TopicExchange redefinePassword = new TopicExchange(RECOVERY_PASSWORD);

        Binding activateAccountEmailBinding = this.binding(emailQueue, activeAccount);
        Binding recoveryPasswordEmailBinding = this.binding(emailQueue, redefinePassword);

        // create queue, exchange and binding both in rabbit
        this.amqpAdmin.declareQueue(emailQueue);

        this.amqpAdmin.declareExchange(activeAccount);
        this.amqpAdmin.declareExchange(redefinePassword);

        this.amqpAdmin.declareBinding(activateAccountEmailBinding);
        this.amqpAdmin.declareBinding(recoveryPasswordEmailBinding);

    }
}
