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

    // 2. GPS Queue 5개 개별 선언
    @Bean public Queue gpsQueue1() { return QueueBuilder.durable("gps.queue.1").build(); }
    @Bean public Queue gpsQueue2() { return QueueBuilder.durable("gps.queue.2").build(); }
    @Bean public Queue gpsQueue3() { return QueueBuilder.durable("gps.queue.3").build(); }
    @Bean public Queue gpsQueue4() { return QueueBuilder.durable("gps.queue.4").build(); }
    @Bean public Queue gpsQueue5() { return QueueBuilder.durable("gps.queue.5").build(); }

    // 3. GPS 바인딩 5개 개별 선언
    @Bean public Binding gpsBinding1(DirectExchange gpsExchange, Queue gpsQueue1) {
        return BindingBuilder.bind(gpsQueue1).to(gpsExchange).with("gps.key.1");
    }

    @Bean public Binding gpsBinding2(DirectExchange gpsExchange, Queue gpsQueue2) {
        return BindingBuilder.bind(gpsQueue2).to(gpsExchange).with("gps.key.2");
    }

    @Bean public Binding gpsBinding3(DirectExchange gpsExchange, Queue gpsQueue3) {
        return BindingBuilder.bind(gpsQueue3).to(gpsExchange).with("gps.key.3");
    }

    @Bean public Binding gpsBinding4(DirectExchange gpsExchange, Queue gpsQueue4) {
        return BindingBuilder.bind(gpsQueue4).to(gpsExchange).with("gps.key.4");
    }

    @Bean public Binding gpsBinding5(DirectExchange gpsExchange, Queue gpsQueue5) {
        return BindingBuilder.bind(gpsQueue5).to(gpsExchange).with("gps.key.5");
    }

    // 4. 기타 Queue
    @Bean public Queue carOnQueue() {
        return QueueBuilder.durable("car.on.queue").build();
    }

    @Bean public Queue carOffQueue() {
        return QueueBuilder.durable("car.off.queue").build();
    }

    @Bean public Binding carOnBinding(DirectExchange carOnExchange, Queue carOnQueue) {
        return BindingBuilder.bind(carOnQueue).to(carOnExchange).with("car.on.key");
    }

    @Bean public Binding carOffBinding(DirectExchange carOffExchange, Queue carOffQueue) {
        return BindingBuilder.bind(carOffQueue).to(carOffExchange).with("car.off.key");
    }

    // 5. 연결/전송/변환 설정
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
