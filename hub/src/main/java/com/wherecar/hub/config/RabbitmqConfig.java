package com.wherecar.hub.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
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
@Slf4j
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

    // 1. Exchange 선언
    @Bean
    public DirectExchange gpsExchange() {
        return new DirectExchange("gps.exchange");
    }

    @Bean
    public DirectExchange carOnExchange() {
        return new DirectExchange("car.on.exchange");
    }

    @Bean
    public DirectExchange carOffExchange() {
        return new DirectExchange("car.off.exchange");
    }

    // 2. Queue 선언
    @Bean
    public List<Queue> gpsQueues() {
        List<Queue> queues = new ArrayList<>();
        for (int i = 1; i <= GPS_QUEUE_COUNT; i++) {
            queues.add(QueueBuilder.durable("gps.queue." + i).build());
        }
        return queues;
    }

    @Bean
    public Queue carOnQueue() {
        return QueueBuilder.durable("car.on.queue").build();
    }

    @Bean
    public Queue carOffQueue() {
        return QueueBuilder.durable("car.off.queue").build();
    }

    // 3. Binding 선언
    @Bean
    public List<Binding> gpsBindings(DirectExchange gpsExchange, List<Queue> gpsQueues) {
        List<Binding> bindings = new ArrayList<>();
        for (int i = 0; i < gpsQueues.size(); i++) {
            log.info(String.valueOf(gpsQueues.get(i)));
            Queue queue = gpsQueues.get(i); // ✅ 실제 Bean을 참조
            bindings.add(BindingBuilder
                    .bind(queue)
                    .to(gpsExchange)
                    .with("gps.key." + (i + 1)));
        }
        return bindings;
    }


    @Bean
    public Binding carOnBinding(DirectExchange carOnExchange, Queue carOnQueue) {
        return BindingBuilder.bind(carOnQueue).to(carOnExchange).with("car.on.key");
    }

    @Bean
    public Binding carOffBinding(DirectExchange carOffExchange, Queue carOffQueue) {
        return BindingBuilder.bind(carOffQueue).to(carOffExchange).with("car.off.key");
    }

    // 4. RabbitMQ 연결
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    // 5. 메시지 변환기 (JSON)
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 6. RabbitTemplate 설정
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
