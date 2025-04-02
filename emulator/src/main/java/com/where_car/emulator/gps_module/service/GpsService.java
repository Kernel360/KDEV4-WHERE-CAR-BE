package com.where_car.emulator.gps_module.service;

import com.where_car.emulator.gps_module.dto.GpsPointDto;
import org.springframework.stereotype.Service;

@Service
public class GpsService {

  /**
   * 두 지점 간의 거리를 계산합니다.
   * @param lat1 첫 번째 지점의 위도
   * @param lon1 첫 번째 지점의 경도
   * @param lat2 두 번째 지점의 위도
   * @param lon2 두 번째 지점의 경도
   * @return 두 지점 간의 거리 (미터 단위)
   */
  public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    final int R = 6371000; // 지구의 반지름 (미터 단위)
    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // 거리 (미터 단위)
  }

  /**
   * 거리를 기반으로 속도를 계산합니다.
   * @param distance 거리 (미터 단위)
   * @param timeInSeconds 시간 (초 단위)
   * @return 속도 (km/h 단위)
   */
  public double calculateSpeed(double distance, double timeInSeconds) {
    return distance / timeInSeconds * 3.6; // 속도 (km/h 단위)
  }

  /**
   * 두 지점 간의 방위를 계산합니다.
   * @param lat1 첫 번째 지점의 위도
   * @param lon1 첫 번째 지점의 경도
   * @param lat2 두 번째 지점의 위도
   * @param lon2 두 번째 지점의 경도
   * @return 방위 (도 단위)
   */
  public double calculateBearing(double lat1, double lon1, double lat2, double lon2) {
    double longitude1 = Math.toRadians(lon1);
    double longitude2 = Math.toRadians(lon2);
    double latitude1 = Math.toRadians(lat1);
    double latitude2 = Math.toRadians(lat2);

    double longDiff = longitude2 - longitude1;
    double y = Math.sin(longDiff) * Math.cos(latitude2);
    double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

    return (Math.toDegrees(Math.atan2(y, x)) + 360) % 360; // 방위 (도 단위)
  }

  /**
   * 여러 지점 간의 누적 거리를 계산합니다.
   * @param gpsPoints GPS 지점 배열
   * @return 누적 거리 (미터 단위)
   */
  public double calculateCumulativeDistance(GpsPointDto[] gpsPoints) {
    double totalDistance = 0.0;
    for (int i = 1; i < gpsPoints.length; i++) {
      totalDistance += calculateDistance(
          gpsPoints[i - 1].getCurLat(), gpsPoints[i - 1].getCurLon(),
          gpsPoints[i].getCurLat(), gpsPoints[i].getCurLon());
    }
    return totalDistance; // 누적 거리 (미터 단위)
  }
}