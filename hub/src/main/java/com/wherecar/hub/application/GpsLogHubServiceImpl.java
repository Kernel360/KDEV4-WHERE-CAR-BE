package com.wherecar.hub.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherecar.hub.application.dto.GpsLogRequest;
import com.wherecar.hub.application.dto.MessageResponse;
import com.wherecar.hub.domain.MessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GpsLogHubServiceImpl implements GpsLogHubService {

    private final RabbitTemplate rabbitTemplate;
    private final MessageFactory messageFactory;

    @Override
    public MessageResponse sendGpsLogMessage(GpsLogRequest gpsLogRequest) {
        try {
            // 객체를 JSON으로 변환후에 큐에 전송합니다.
            ObjectMapper objectMapper = new ObjectMapper();
            String objectToJSON = objectMapper.writeValueAsString(gpsLogRequest);

            log.info("message :: {}", gpsLogRequest.toString());
            log.info("objectToJSON :: {}", objectToJSON);

            rabbitTemplate.convertAndSend("gps.exchange", "gps.key", objectToJSON);
            return messageFactory.toMessageResponse();
        } catch (JsonProcessingException jpe) {
            log.error("파싱 오류 발생", jpe);
            return messageFactory.toErrorMessageResponse("파싱 오류 발생");
        }
    }
}
