package com.wherecar.hub.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wherecar.hub.application.dto.CarLogRequest;
import com.wherecar.hub.application.dto.MessageResponse;
import com.wherecar.hub.domain.MessageFactory;
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

    @Override
    public MessageResponse sendCarOnLogMessage(CarLogRequest onLogRequest) {
        try {
            // 객체를 JSON으로 변환후에 큐에 전송합니다.
            ObjectMapper objectMappper = new ObjectMapper();
            String objectToJSON = objectMappper.writeValueAsString(onLogRequest);

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
            ObjectMapper objectMappper = new ObjectMapper();
            String objectToJSON = objectMappper.writeValueAsString(offLogRequest);

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
