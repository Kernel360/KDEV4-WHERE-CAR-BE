package com.wherecar.collector.gpslog.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.wherecar.collector.carlog.application.CarLogService;
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
public class GpsLogConsumerService {

    private final GpsLogService gpsLogService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "gps.queue", ackMode = "MANUAL")
    public void receiveGpsLog(String message, Channel channel,  @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            log.info("GPS 로그 수신: {}", message);
            GpsLogRequest gpsLogRequest = objectMapper.readValue(message, GpsLogRequest.class);
            gpsLogService.receiveGpsLogs(gpsLogRequest);

            // 정상 처리 후 ack
            channel.basicAck(tag, false);
        } catch (JsonProcessingException e) {
            log.error("GPS 로그 변환 오류: {}", e.getMessage(), e);

            // 처리 실패 시 메시지를 다시 큐로 보내고 싶다면
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ioException) {
                log.error("메시지 Nack 실패", ioException);
            }
        } catch (Exception e) {
            log.error("GPS 로그 처리 중 오류", e);
            try {
                channel.basicAck(tag, false);
//                channel.basicNack(tag, false, true);
            } catch (IOException ioException) {
                log.error("메시지 Nack 실패", ioException);
            }
        }
    }
}
