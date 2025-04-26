package com.wherecar.hub.common;

import com.wherecar.hub.common.application.dto.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageFactory {

    public MessageResponse toMessageResponse(){

        return MessageResponse.builder()
                .resultCode("200")
                .resultMessage("success")
                .build();
    }

    public MessageResponse toErrorMessageResponse(String errorMessage){
        return MessageResponse.builder()
                .resultCode("500")
                .resultMessage(errorMessage)
                .build();
    }
}
