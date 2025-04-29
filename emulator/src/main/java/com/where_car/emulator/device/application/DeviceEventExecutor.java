package com.where_car.emulator.device.application;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.where_car.emulator.device.application.dto.CarRequest;
import com.where_car.emulator.device.application.dto.CycleInfoRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * 차량 이벤트를 API 서버로 전송하는 컴포넌트
 */
@Slf4j
@Component
public class DeviceEventExecutor {
    
    private final RestTemplate restTemplate;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Value("${emulator.endpoints.hub.on-path}")
    private String startEndpoint;

    @Value("${emulator.endpoints.hub.cycle-info-path}")
    private String cycleEndpoint;

    @Value("${emulator.endpoints.hub.off-path}")
    private String stopEndpoint;

    private static final int RETRY_DELAY_SECONDS = 60;
    private static final int MAX_IMMEDIATE_RETRIES = 5;
    private static final int IMMEDIATE_RETRY_DELAY_MS = 500;
    private static final String TOKEN_MISSING_CODE = "200";
    private static final String TOKEN_INVALID_CODE = "201";

    public DeviceEventExecutor(@Qualifier("hubTemplate") RestTemplate restTemplate, TokenService tokenService, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.tokenService = tokenService;
        this.objectMapper = objectMapper;
    }

    public void sendCycleInfo(CycleInfoRequest cycleInfoRequest) {
        log.info("CycleInfo 전송 [{}] : {}", cycleInfoRequest.getCCnt(), cycleInfoRequest);
        sendRequestWithRetry(cycleEndpoint, cycleInfoRequest, "주기 정보 API", 0);
    }

    public void sendCarStart(CarRequest carStartRequest) {
        log.info("CarStartData 전송: {}", carStartRequest);
        sendRequestWithRetry(startEndpoint, carStartRequest, "시동 ON 정보 API", 0);
    }

    public void sendCarStop(CarRequest carStopRequest) {
        log.info("CarStopData 전송: {}", carStopRequest);
        sendRequestWithRetry(stopEndpoint, carStopRequest, "시동 OFF 정보 API", 0);
    }
    
    private <T> void sendRequestWithRetry(String url, T requestDto, String action, int retryCount) {
        String mdn = extractMdn(requestDto);
        
        try {
            String token = tokenService.getToken(mdn);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Token", token);

            HttpEntity<T> entity = new HttpEntity<>(requestDto, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            handleResponse(response, mdn, url, requestDto, action, retryCount);
        } catch (HttpClientErrorException e) {
            handleRequestException(e, url, requestDto, action, retryCount);
        } catch (Exception e) {
            log.error("{} 정보 전달 중 예외 발생: {}", action, e.getMessage());
            handleRetry(url, requestDto, action, retryCount);
        }
    }

    private <T> void handleRequestException(HttpClientErrorException e, String url, T requestDto, String action, int retryCount) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            log.error("{} 정보 전달 실패: 서버 API를 호출할 수 없습니다.", action);
        } else {
            log.error("{} 정보 전달 실패: 알 수 없는 오류가 발생했습니다. {}", action, e.getMessage());
        }
        handleRetry(url, requestDto, action, retryCount);
    }

    private <T> void handleResponse(ResponseEntity<String> response, String mdn, String url, T requestDto, String action, int retryCount) {
        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            String rstCd = root.path("rstCd").asText();

            if ("000".equals(rstCd)) {
                log.info("{} 정보 전송 성공", action);
            } else if (TOKEN_MISSING_CODE.equals(rstCd) || TOKEN_INVALID_CODE.equals(rstCd)) {
                log.warn("토큰 오류 발생 (코드: {}): {}", rstCd, root.path("rstMsg").asText());
                // 토큰 무효화 및 재발급
                String newToken = tokenService.invalidateAndGetNewToken(mdn);

                retryWithNewToken(url, requestDto, action, mdn, newToken, retryCount);
            } else {
                log.error("{} 정보 전송 실패. 응답 코드: {}, 메시지: {}",
                    action, rstCd, root.path("rstMsg").asText());
                handleRetry(url, requestDto, action, retryCount);
            }
        } catch (Exception e) {
            log.error("응답 처리 중 오류 발생: {}", e.getMessage());
            handleRetry(url, requestDto, action, retryCount);
        }
    }

    private <T> void retryWithNewToken(String url, T requestDto, String action, String mdn, String token, int retryCount) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Token", token);

            HttpEntity<T> entity = new HttpEntity<>(requestDto, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            handleResponse(response, mdn, url, requestDto, action, retryCount);
        } catch (Exception e) {
            log.error("토큰 재발급 후 재시도 중 오류 발생: {}", e.getMessage());
            handleRetry(url, requestDto, action, retryCount);
        }
    }

    private <T> String extractMdn(T requestDto) {
        try {
            // CarDto와 CycleInfoRequest 모두 mdn 필드를 가지고 있다고 가정
            return objectMapper.convertValue(requestDto, JsonNode.class)
                .path("mdn").asText("00");  // 기본값으로 "00" 사용
        } catch (Exception e) {
            log.error("MDN 추출 중 오류 발생: {}", e.getMessage());
            return "00"; // 기본 MDN 값
        }
    }

    /**
     * 재시도 로직 처리
     * 5회까지는 즉시 재시도, 이후에는 1분 대기 후 재시도
     */
    private <T> void handleRetry(String url, T requestDto, String action, int retryCount) {
        if (retryCount < MAX_IMMEDIATE_RETRIES) {
            // 즉시 재시도 (5회)
            try {
                log.info("{} 정보 즉시 재시도 중... (시도 {}/{})", action, retryCount + 1, MAX_IMMEDIATE_RETRIES);
                Thread.sleep(IMMEDIATE_RETRY_DELAY_MS);
                sendRequestWithRetry(url, requestDto, action, retryCount + 1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("{} 정보 즉시 재시도 중 인터럽트 발생: {}", action, e.getMessage());
                retryAfterDelay(url, requestDto, action);
            }
        } else {
            // 모든 즉시 재시도가 실패한 경우, 1분 후 재시도
            log.warn("{} 정보 전송 실패 ({}회 시도). 1분 후 재시도합니다.", action, MAX_IMMEDIATE_RETRIES);
            retryAfterDelay(url, requestDto, action);
        }
    }

    private <T> void retryAfterDelay(String url, T requestDto, String action) {
        try {
            Thread.sleep(RETRY_DELAY_SECONDS * 1000L);
            // 지연 후 재시도 시 카운트를 0으로 초기화하여 다시 즉시 재시도 5회를 수행할 수 있게 함
            sendRequestWithRetry(url, requestDto, action, 0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("{} 정보 재시도 중 인터럽트 발생: {}", action, e.getMessage());
        }
    }
}
