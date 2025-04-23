package com.wherecar.hub.carlog.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherecar.hub.carlog.application.dto.CarLogRequest;
import com.wherecar.hub.common.application.dto.MessageResponse;
import com.wherecar.hub.common.MessageFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarLogHubServiceImpl implements CarLogHubService {

    private final RabbitTemplate rabbitTemplate;
    private final MessageFactory messageFactory;
    private final ObjectMapper objectMapper;

    @Override
    public MessageResponse sendCarOnLogMessage(CarLogRequest onLogRequest) {
        try {
            // 객체를 JSON으로 변환후에 큐에 전송합니다.
            String objectToJSON = objectMapper.writeValueAsString(onLogRequest);

            log.info("message :: {}", onLogRequest.toString());
            log.info("objectToJSON :: {}", objectToJSON);

            rabbitTemplate.convertAndSend("car.on.exchange", "car.on.key", objectToJSON);
            return messageFactory.toMessageResponse();
        } catch (JsonProcessingException jpe) {
            log.error("파싱 오류 발생", jpe);
            return messageFactory.toErrorMessageResponse("파싱 오류 발생");
        }
    }

    @Override
    public MessageResponse sendCarOffLogMessage(CarLogRequest offLogRequest) {
        try {
            // 객체를 JSON으로 변환후에 큐에 전송합니다.
            String objectToJSON = objectMapper.writeValueAsString(offLogRequest);

            log.info("message :: {}", offLogRequest.toString());
            log.info("objectToJSON :: {}", objectToJSON);

            rabbitTemplate.convertAndSend("car.off.exchange", "car.off.key", objectToJSON);
            return messageFactory.toMessageResponse();
        } catch (JsonProcessingException jpe) {
            log.error("파싱 오류 발생", jpe);
            return messageFactory.toErrorMessageResponse("파싱 오류 발생");
        }
    }
}
