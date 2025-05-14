package com.wherecar.hub.gpslog.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherecar.hub.common.MessageFactory;
import com.wherecar.hub.gpslog.application.dto.GpsLogRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GpsLogHubServiceImpl implements GpsLogHubService {

    private final RabbitTemplate rabbitTemplate;
    private final MessageFactory messageFactory;
    private final ObjectMapper objectMapper;

    @Override
    @Async
    public void sendGpsLogMessage(GpsLogRequest gpsLogRequest) {
        try {
//            int shard = (int)(Math.random() * 3) + 1;
            int shard = 1;
            String routingKey = "gps.key." + shard;

            String objectToJSON = objectMapper.writeValueAsString(gpsLogRequest);

            log.info("[sendGpsLogMessage] shard={}, routingKey={}", shard, routingKey);
            log.info("[sendGpsLogMessage] objectToJSON={}", objectToJSON);

            rabbitTemplate.convertAndSend("gps.exchange", routingKey, objectToJSON);
        } catch (JsonProcessingException jpe) {
            log.error("❌ JSON 파싱 오류 발생", jpe);
        } catch (Exception e) {
            log.error("❌ hub / GPS 로그 비동기 처리 예외 발생", e);
        }
    }
}


//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class GpsLogHubServiceImpl implements GpsLogHubService {
//
//    private final RabbitTemplate rabbitTemplate;
//    private final MessageFactory messageFactory;
//    private final ObjectMapper objectMapper;
//
//    @Override
//    public MessageResponse sendGpsLogMessage(GpsLogRequest gpsLogRequest) {
//        try {
//            // 객체를 JSON으로 변환후에 큐에 전송합니다.
//            String objectToJSON = objectMapper.writeValueAsString(gpsLogRequest);
//
//            log.info("message :: {}", gpsLogRequest.toString());
//            log.info("objectToJSON :: {}", objectToJSON);
//
//            rabbitTemplate.convertAndSend("gps.exchange", "gps.key", objectToJSON);
//            return messageFactory.toMessageResponse();
//        } catch (JsonProcessingException jpe) {
//            log.error("파싱 오류 발생", jpe);
//            return messageFactory.toErrorMessageResponse("파싱 오류 발생");
//        }
//    }
//}
