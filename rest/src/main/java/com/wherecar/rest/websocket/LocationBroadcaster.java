package com.wherecar.rest.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocationBroadcaster {

    private final CarLocationSocketHandler handler;

    @Scheduled(cron = "0 * * * * *") //정분마다 실행
    public void broadcast() {
        log.info("LocationBroadcaster.broadcast() 호출됨 - 위치 업데이트 전송 시도");
        handler.sendLocationUpdates();
    }
}