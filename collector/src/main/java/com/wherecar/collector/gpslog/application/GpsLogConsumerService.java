package com.wherecar.collector.gpslog.application;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

public interface GpsLogConsumerService {
    void receiveGpsLog(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException;
}
