package com.wherecar.collector.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.DirectExchange;
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

    // Exchange만 참조용으로 선언
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

    // JSON 메시지 변환기
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 리스너 컨테이너 설정
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
        factory.setPrefetchCount(10); // 병렬 처리 성능 튜닝
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO); // 메시지 자동 ack
        return factory;
    }
}

