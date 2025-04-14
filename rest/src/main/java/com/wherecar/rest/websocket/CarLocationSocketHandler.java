package com.wherecar.rest.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wherecar.rest.dto.CarResponse;
import com.wherecar.rest.dto.GpsLogResponse;
import com.wherecar.rest.dto.GpsPoint;
import com.wherecar.rest.dto.GpsRouteResponse;
import com.wherecar.rest.service.CarService;
import com.wherecar.rest.service.GpsLogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class CarLocationSocketHandler extends TextWebSocketHandler {
    //정인재 <
    private static Integer count = 0;
    // 정인재 >

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final GpsLogService gpsLogService;
    private final CarService carService;
    private final Map<WebSocketSession, Long> companySubscriptions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("클라이언트 연결됨: " + session.getId());
        companySubscriptions.put(session, null);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        JsonNode json = objectMapper.readTree(message.getPayload());

        if ("subscribe".equals(json.get("type").asText())) {
            Long companyId = json.get("companyId").asLong();
            companySubscriptions.put(session, companyId);
        }

        sendLocationUpdates();
    }

    public void sendLocationUpdates() {

        companySubscriptions.forEach((session, companyId) -> {
            if (session.isOpen() && companyId != null) {

                // 전체 차량 데이터 가줘오기
                List<CarResponse> cars = carService.gatCarsByStatus(companyId);

                for(CarResponse car : cars) {
                 log.info("차량 정보"+ car.getMdn());
                }

                List<Map<String, Object>> responseList = new ArrayList<>();

                for (CarResponse car : cars) {

                    //TODO: 실제 시간으로 변경
                    //LocalDateTime now = LocalDateTime.now().withNano(0);
                    LocalDateTime now;
                    if(count%3==0) {
                        now = LocalDateTime.of(2025, 4, 10, 16, 51, 0, 0);
                    } else if (count%3==1) {
                        now = LocalDateTime.of(2025, 4, 10, 16, 52, 0, 0);
                    } else {
                        now = LocalDateTime.of(2025, 4, 10, 16, 53, 0, 0);
                    }

                    LocalDateTime baseTime = now.minusMinutes(1);

                    String carMdn = car.getMdn();

                    // 1분간의 GPS 데이터 조회
                    GpsRouteResponse result = gpsLogService.getRoute(carMdn, baseTime, now);
                    log.info("경로 결과"+ result.getRoute());

                    // 60초 기준 데이터 보정
                    List<GpsPoint> filledPoints = fillTo60(carMdn, result.getRoute(), baseTime);

                    Map<String, Object> carData = Map.of(
                            "carId", carMdn,
                            "locations", filledPoints
                    );

                    responseList.add(carData);

                }


                try {
                    String json = objectMapper.writeValueAsString(responseList);
                    session.sendMessage(new TextMessage(json));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                count++;
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        companySubscriptions.remove(session);
    }

    // 60개 미만이면 마지막 좌표 복사해서 채우기
    private List<GpsPoint> fillTo60(String mdn, List<GpsPoint> rawList, LocalDateTime baseTime) {
        List<GpsPoint> result = new ArrayList<>();
        Map<LocalDateTime, GpsPoint> gpsMap = new HashMap<>();

        // 시간 기준 Map으로 정리 (초 단위 정렬)
        for (GpsPoint point : rawList) {
            LocalDateTime ts = point.getTimestamp().withNano(0);
            gpsMap.put(ts, point);
        }

        GpsPoint latest = null;

        //rawList가 비어 있으면 미리 최신 위치 한 번만 조회
        if (rawList.isEmpty()) {
            GpsLogResponse latestLocation = gpsLogService.getLatestLocation(mdn);

            for (int i = 0; i < 60; i++) {
                LocalDateTime currentTime = baseTime.plusSeconds(i);

                GpsPoint point;
                if (latestLocation != null) {
                    point = GpsPoint.builder()
                            .latitude(latestLocation.getLatitude())
                            .longitude(latestLocation.getLongitude())
                            .timestamp(currentTime)
                            .build();
                } else {
                    point = GpsPoint.builder()
                            .latitude(0.0)
                            .longitude(0.0)
                            .timestamp(currentTime)
                            .build();
                }

                result.add(point);
            }

            return result;
        }

        for (int i = 0; i < 60; i++) {
            LocalDateTime currentTime = baseTime.plusSeconds(i);
            GpsPoint point;

            if (gpsMap.containsKey(currentTime)) {
                point = gpsMap.get(currentTime);
                latest = point;

                log.info("gpsMap에서 찾은 좌표: {}", point);

            } else if (latest != null) {
                point = GpsPoint.builder()
                        .latitude(latest.getLatitude())
                        .longitude(latest.getLongitude())
                        .timestamp(currentTime)
                        .build();

                log.info("최신 좌표 사용: {}", point);

            } else {
                GpsLogResponse latestLocation = gpsLogService.getLatestLocation(mdn);
                if (latestLocation != null) {
                    point = GpsPoint.builder()
                            .latitude(latestLocation.getLatitude())
                            .longitude(latestLocation.getLongitude())
                            .timestamp(currentTime)
                            .build();
                    latest = point;

                    log.info("최신 위치 정보 사용: {}", point);

                } else {
                    point = GpsPoint.builder()
                            .latitude(0.0)
                            .longitude(0.0)
                            .timestamp(currentTime)
                            .build();
                    latest = point;

                    log.info("기본 값 사용 (0.0, 0.0): {}", point);
                }
            }

            result.add(point);
        }

        return result;
    }

}
