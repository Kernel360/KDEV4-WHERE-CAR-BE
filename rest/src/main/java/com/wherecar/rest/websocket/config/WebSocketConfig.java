package com.wherecar.rest.websocket.config;

import com.wherecar.rest.websocket.CarLocationSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketConfigurer {

    private final CarLocationSocketHandler carLocationSocketHandler; // 스프링이 자동 주입

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(carLocationSocketHandler, "/ws") // 스프링 빈 사용!
                .setAllowedOrigins("*");
    }
}
