package com.wherecar.collector.carlog.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.wherecar.collector.carlog.application.dto.CarLogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarLogConsumerServiceImpl implements CarLogConsumerService {

    private final CarLogService carLogService;
    private final ObjectMapper objectMapper;

    @Override
    @RabbitListener(queues = "car.on.queue", ackMode = "MANUAL")
    public void receiveOnLog(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("ON 로그 수신: {}", message);

        CarLogRequest carLogRequest = objectMapper.readValue(message, CarLogRequest.class);
        carLogService.receiveOnLog(carLogRequest);

        // 정상 처리 후 ack
        channel.basicAck(tag, false);
    }

    @Override
    @RabbitListener(queues = "car.off.queue", ackMode = "MANUAL")
    public void receiveOffLog(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("OFF 로그 수신: {}", message);

        CarLogRequest carLogRequest = objectMapper.readValue(message, CarLogRequest.class);
        carLogService.receiveOffLog(carLogRequest);

        // 정상 처리 후 ack
        channel.basicAck(tag, false);
    }

}
