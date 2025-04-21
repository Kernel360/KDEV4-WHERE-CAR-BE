package com.where_car.emulator.global.utill;

public class GpsUtils {

	private static final int EARTH_RADIUS_METERS = 6371000;
	private static final double KM_PER_HOUR_CONVERSION = 3.6;

	private GpsUtils() {
		throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다");
	}

	/**
	 * 두 지점 간의 거리를 계산합니다.
	 * @param lat1 첫 번째 지점의 위도
	 * @param lon1 첫 번째 지점의 경도
	 * @param lat2 두 번째 지점의 위도
	 * @param lon2 두 번째 지점의 경도
	 * @return 두 지점 간의 거리 (미터 단위)
	 */
	public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
			+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
			* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return EARTH_RADIUS_METERS * c; // 거리 (미터 단위)
	}

	/**
	 * 거리를 기반으로 속도를 계산합니다.
	 * @param distance 거리 (미터 단위)
	 * @param timeInSeconds 시간 (초 단위)
	 * @return 속도 (km/h 단위)
	 */
	public static double calculateSpeed(double distance, double timeInSeconds) {
		return distance / timeInSeconds * KM_PER_HOUR_CONVERSION; // 속도 (km/h 단위)
	}

	/**
	 * 두 지점 간의 방위를 계산합니다.
	 * @param lat1 첫 번째 지점의 위도
	 * @param lon1 첫 번째 지점의 경도
	 * @param lat2 두 번째 지점의 위도
	 * @param lon2 두 번째 지점의 경도
	 * @return 방위 (도 단위)
	 */
	public static int calculateBearing(double lat1, double lon1, double lat2, double lon2) {
		double longitude1 = Math.toRadians(lon1);
		double longitude2 = Math.toRadians(lon2);
		double latitude1 = Math.toRadians(lat1);
		double latitude2 = Math.toRadians(lat2);

		double longDiff = longitude2 - longitude1;
		double y = Math.sin(longDiff) * Math.cos(latitude2);
		double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

		return (int) ((Math.toDegrees(Math.atan2(y, x)) + 360) % 360); // 방위 (도 단위)
	}
}
