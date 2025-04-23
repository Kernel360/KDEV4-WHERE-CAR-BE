package com.wherecar.collector.carlog.application;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

public interface CarLogConsumerService {
    void receiveOnLog(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag);
    void receiveOffLog(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag);
}
