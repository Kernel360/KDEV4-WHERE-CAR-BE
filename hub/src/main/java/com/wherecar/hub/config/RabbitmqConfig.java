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

    /**
    * 1. Exchange 구성
    * "hello.exchange" 라는 이름으로 Driect Exchange를 생성.
    *
    * @return DirectExchange
    */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("hello.exchange");
    }

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

    /**
    * 2. 큐를 구성
    * "hello.queue"의 이름으로 큐를 구성
    *
    * @return Queue
    */
    @Bean
    Queue queue() {
        return new Queue("hello.queue", true);
    }

    @Bean
    Queue carOnQueue() {
        return new Queue("car.on.queue", true);
    }

    @Bean
    Queue carOffQueue() {
        return new Queue("car.off.queue", true);
    }

    @Bean
    Queue gpsQueue() {
        return new Queue("gps.queue", true);
    }

    /**
    * 3. 큐와 DirectExchange를 바인딩
    * "hello.key"의 이름으로 바인딩 구성
    *
    * @param directExchange
    * @param queue
    * @return Binding
    */
    @Bean
    Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("hello.key");
    }

    @Bean
    Binding carOnBinding(DirectExchange carOnExchange, Queue carOnQueue) {
        return BindingBuilder.bind(carOnQueue).to(carOnExchange).with("car.on.key");
    }

    @Bean
    Binding carOffBinding(DirectExchange carOffExchange, Queue carOffQueue) {
        return BindingBuilder.bind(carOffQueue).to(carOffExchange).with("car.off.key");
    }

    @Bean
    Binding gpsBinding(DirectExchange gpsExchange, Queue gpsQueue) {
        return BindingBuilder.bind(gpsQueue).to(gpsExchange).with("gps.key");
    }

    /**
    * 4. RabbitMQ와의 연결을 위한 connetionFactory를 구성
    * Application.properties의 RabbitMQ의 사용자 정보를 가져와서 RabbitMQ와의 연결에 필요한 ConnetionFactory 구성
    *
    * @return ConnectionFactory
    */
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }


    /**
     * 5. 메시지를 전송하고 수신하기 위한 JSON 타입으로 메시지를 변경합니다.
     * Jackson2JsonMessageConverter를 사용하여 메시지 변환을 수행합니다. JSON 형식으로 메시지를 전송하고 수신할 수 있습니다
     *
     * @return
     */
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * 6. 구성한 ConnectionFactory, MessageConverter를 통해 템플릿을 구성합니다.
     *
     * @param connectionFactory
     * @param messageConverter
     * @return
     */
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}
