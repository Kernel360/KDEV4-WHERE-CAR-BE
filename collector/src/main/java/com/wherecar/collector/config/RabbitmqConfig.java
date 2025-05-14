package com.wherecar.collector.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableRabbit
public class RabbitmqConfig {

    private static final int GPS_QUEUE_COUNT = 5; // 원하는 shard 수

    @Bean
    public DirectExchange gpsExchange() {
        return new DirectExchange("gps.exchange");
    }

    @Bean
    public List<Queue> gpsLogQueues() {
        List<Queue> queues = new ArrayList<>();
        for (int i = 1; i <= GPS_QUEUE_COUNT; i++) {
            queues.add(new Queue("gps.queue." + i, true));
        }
        return queues;
    }

    @Bean
    public List<Binding> gpsLogBindings(DirectExchange gpsExchange, List<Queue> gpsLogQueues) {
        List<Binding> bindings = new ArrayList<>();
        for (int i = 0; i < gpsLogQueues.size(); i++) {
            Queue queue = gpsLogQueues.get(i); // ✅ 이미 등록된 Bean 사용
            String routingKey = "gps.key." + (i + 1);
            bindings.add(BindingBuilder.bind(queue).to(gpsExchange).with(routingKey));
        }
        return bindings;
    }
    // 다른 car 관련 큐 설정 그대로 유지
    @Bean
    public DirectExchange carOnExchange() {
        return new DirectExchange("car.on.exchange");
    }

    @Bean
    public Queue carOnQueue() {
        return new Queue("car.on.queue", true);
    }

    @Bean
    public Binding carOnBinding() {
        return BindingBuilder.bind(carOnQueue()).to(carOnExchange()).with("car.on.key");
    }

    @Bean
    public DirectExchange carOffExchange() {
        return new DirectExchange("car.off.exchange");
    }

    @Bean
    public Queue carOffQueue() {
        return new Queue("car.off.queue", true);
    }

    @Bean
    public Binding carOffBinding() {
        return BindingBuilder.bind(carOffQueue()).to(carOffExchange()).with("car.off.key");
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
        factory.setPrefetchCount(50);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}

