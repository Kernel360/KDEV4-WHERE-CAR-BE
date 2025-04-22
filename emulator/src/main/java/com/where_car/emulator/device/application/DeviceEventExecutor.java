package com.where_car.emulator.device.application;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.where_car.emulator.device.application.dto.CarDto;
import com.where_car.emulator.device.application.dto.CycleInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 차량 이벤트를 API 서버로 전송하는 컴포넌트
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceEventExecutor {
    
    private final RestTemplate restTemplate;
    private static final int RETRY_DELAY_SECONDS = 60;
    
    /**
     * 차량 시동 ON 이벤트 전송
     */
    public void sendCarStart(CarDto carStartDto) {
        log.info("CarStartData 전송: {}", carStartDto);
        sendRequestWithRetry("/api/on", carStartDto, "시동 ON 정보 API");
    }
    
    /**
     * 주기 정보 전송
     */
    public void sendCycleInfo(CycleInfoDto cycleInfoDto) {
        log.info("CycleInfo 전송: {}", cycleInfoDto);
        sendRequestWithRetry("/api/gps", cycleInfoDto, "주기 정보 API");
    }
    
    /**
     * 차량 시동 OFF 이벤트 전송
     */
    public void sendCarStop(CarDto carStopDto) {
        log.info("CarStopData 전송: {}", carStopDto);
        sendRequestWithRetry("/api/off", carStopDto, "시동 OFF 정보 API");
    }
    
    private void sendRequestWithRetry(String url, Object requestDto, String action) {
        try {
            restTemplate.postForObject(url, requestDto, requestDto.getClass());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error("{} 정보 전달 실패: 서버 API를 호출할 수 없습니다, 1분 후 재시도 합니다.", action);
            } else {
                log.error("{} 정보 전달 실패: 알 수 없는 오류가 발생했습니다, 1분 후 재시도 합니다. {}", action, e.getMessage());
            }
            retryAfterDelay(url, requestDto, action);
        }
    }

    private void retryAfterDelay(String url, Object requestDto, String action) {
        try {
            Thread.sleep(RETRY_DELAY_SECONDS * 1000L);
            sendRequestWithRetry(url, requestDto, action);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("{} 정보 재시도 중 인터럽트 발생: {}", action, e.getMessage());
        }
    }
}
