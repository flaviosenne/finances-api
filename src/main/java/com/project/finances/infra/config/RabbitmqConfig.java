package com.project.finances.infra.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.project.finances.infra.adapters.rabbitmq.RoutingKeys.ACTIVATE_ACCOUNT;
import static com.project.finances.infra.adapters.rabbitmq.RoutingKeys.RECOVERY_PASSWORD;

@Component
@AllArgsConstructor
public class RabbitmqConfig {
    private final String EXCHANGE = "amq.direct";

    private final AmqpAdmin amqpAdmin;

    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange exchange(){
        return new DirectExchange(EXCHANGE);
    }

    private Binding binding(Queue queue, DirectExchange exchange, String routingKey){
        String destination = queue.getName();
        Binding.DestinationType destinationType = Binding.DestinationType.QUEUE;
        String exchangeName = exchange().getName();
        return new Binding(destination, destinationType, exchangeName, routingKey, null);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @PostConstruct
    private void initializeQueue(){
        Queue activeAccountEmailQueue = this.queue("email");
        Queue recoveryPasswordEmailQueue = this.queue("email");

        DirectExchange exchange = this.exchange();

        Binding activateAccountEmailBinding = this.binding(activeAccountEmailQueue, exchange, ACTIVATE_ACCOUNT);
        Binding recoveryPasswordEmailBinding = this.binding(recoveryPasswordEmailQueue, exchange, RECOVERY_PASSWORD);

        // create queue, binding, exchange in rabbit
        this.amqpAdmin.declareQueue(activeAccountEmailQueue);
        this.amqpAdmin.declareQueue(recoveryPasswordEmailQueue);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(activateAccountEmailBinding);
        this.amqpAdmin.declareBinding(recoveryPasswordEmailBinding);

    }
}
