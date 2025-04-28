package com.where_car.emulator.global.utill;

import java.util.List;

import org.w3c.dom.Element;

public class CoordinateUtils {

    private CoordinateUtils() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다");
    }

    /**
     * 좌표 값의 형식을 변환합니다.
     * 소수점 6자리까지 포맷한 후 소수점을 제거합니다.
     * 
     * @param value 좌표 문자열
     * @return 포맷된 좌표 문자열
     */
    public static String formatCoordinate(String value) {
        // 문자열을 double로 변환
        double doubleValue = Double.parseDouble(value);
        // 소수점 6자리까지 포맷
        String formattedValue = java.lang.String.format("%.6f", doubleValue);
        // 소수점 제거
        return formattedValue.replace(".", "");
    }

    public static String calculateSpeedFromCoordinates(List<Element> gpxFile) {
        int speed = (int) Math.round(GpsUtils.calculateSpeed(
            GpsUtils.calculateDistance(
                Double.parseDouble(gpxFile.get(0).getAttribute("lat")),
                Double.parseDouble(gpxFile.get(0).getAttribute("lon")),
                Double.parseDouble(gpxFile.get(1).getAttribute("lat")),
                Double.parseDouble(gpxFile.get(1).getAttribute("lon"))
            ),
            1
        ));
        return String.valueOf(speed);
    }

    public static String calculateAngleFromCoordinates(List<Element> gpxFile) {
        int angle = GpsUtils.calculateBearing(
            Double.parseDouble(gpxFile.get(0).getAttribute("lat")),
            Double.parseDouble(gpxFile.get(0).getAttribute("lon")),
            Double.parseDouble(gpxFile.get(1).getAttribute("lat")),
            Double.parseDouble(gpxFile.get(1).getAttribute("lon"))
        );
        return String.valueOf(angle);
    }
}
