package com.wherecar.collector.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
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

}
