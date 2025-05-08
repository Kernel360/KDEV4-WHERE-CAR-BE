package com.wherecar.collector.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitmqConfig {

    @Bean
    public DirectExchange carOnExchange() {
        return new DirectExchange("car.on.exchange");
    }

    @Bean
    public DirectExchange carOffExchange() {
        return new DirectExchange("car.off.exchange");
    }

    @Bean
    public DirectExchange gpsExchange() {
        return new DirectExchange("gps.exchange");
    }

    @Bean
    public Queue carOnQueue() {
        return new Queue("car.on.queue", true);
    }

    @Bean
    public Queue carOffQueue() {
        return new Queue("car.off.queue", true);
    }

    @Bean
    public Queue gpsLogQueue() {
        return new Queue("gps.queue", true);
    }

    @Bean
    public Binding carOnBinding() {
        return BindingBuilder.bind(carOnQueue()).to(carOnExchange()).with("car.on.key");
    }

    @Bean
    public Binding carOffBinding() {
        return BindingBuilder.bind(carOffQueue()).to(carOffExchange()).with("car.off.key");
    }

    @Bean
    public Binding gpsLogBinding() {
        return BindingBuilder.bind(gpsLogQueue()).to(gpsExchange()).with("gps.key");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);

        // 여기서 prefetch count 및 concurrency 설정
        factory.setPrefetchCount(50); // 한 컨슈머가 최대 50개까지 미리 가져올 수 있음
        factory.setConcurrentConsumers(10); // 동시에 실행될 컨슈머 수 (스레드 수)
        factory.setMaxConcurrentConsumers(20); // 필요시 확장 가능한 최대 컨슈머 수
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO); // 또는 MANUAL

        return factory;
    }
}
