package com.wherecar.collector.gpslog.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.wherecar.collector.gpslog.application.dto.GpsLogRequest;
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
public class GpsLogConsumerServiceImpl implements GpsLogConsumerService {

    private final GpsLogService gpsLogService;
    private final ObjectMapper objectMapper;

    @Override
    @RabbitListener(queues = "gps.queue", ackMode = "MANUAL")
    public void receiveGpsLog(String message, Channel channel,  @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        log.info("GPS 로그 수신: {}", message);

        GpsLogRequest gpsLogRequest = objectMapper.readValue(message, GpsLogRequest.class);
        gpsLogService.receiveGpsLogs(gpsLogRequest);

        // 정상 처리 후 ack
        channel.basicAck(tag, false);
    }
}
