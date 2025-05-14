package com.wherecar.hub.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private int port;

    private static final int GPS_QUEUE_COUNT = 5;

    // 1. Exchange
    @Bean
    DirectExchange carOnExchange() {
        return new DirectExchange("car.on.exchange");
    }

    @Bean
    DirectExchange carOffExchange() {
        return new DirectExchange("car.off.exchange");
    }

    @Bean
    DirectExchange gpsExchange() {
        return new DirectExchange("gps.exchange");
    }

    @Bean
    public List<Queue> gpsQueues() {
        List<Queue> queues = new ArrayList<>();
        for (int i = 1; i <= GPS_QUEUE_COUNT; i++) {
            queues.add(new Queue("gps.queue." + i, true));
        }
        return queues;
    }

    @Bean
    public List<Binding> gpsBindings(DirectExchange gpsExchange, List<Queue> gpsQueues) {
        List<Binding> bindings = new ArrayList<>();
        for (int i = 0; i < gpsQueues.size(); i++) {
            Queue queue = gpsQueues.get(i);
            String routingKey = "gps.key." + (i + 1);
            bindings.add(BindingBuilder.bind(queue).to(gpsExchange).with(routingKey));
        }
        return bindings;
    }

    // 기존 car.on/off queue/binding 유지
    @Bean
    Queue carOnQueue() {
        return new Queue("car.on.queue", true);
    }

    @Bean
    Queue carOffQueue() {
        return new Queue("car.off.queue", true);
    }

    @Bean
    Binding carOnBinding(DirectExchange carOnExchange, Queue carOnQueue) {
        return BindingBuilder.bind(carOnQueue).to(carOnExchange).with("car.on.key");
    }

    @Bean
    Binding carOffBinding(DirectExchange carOffExchange, Queue carOffQueue) {
        return BindingBuilder.bind(carOffQueue).to(carOffExchange).with("car.off.key");
    }

    // 4. RabbitMQ 연결
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    // 5. 메시지 변환기 (JSON)
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 6. RabbitTemplate 설정
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}

