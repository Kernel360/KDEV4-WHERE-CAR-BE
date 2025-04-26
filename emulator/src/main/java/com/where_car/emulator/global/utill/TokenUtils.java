package com.where_car.emulator.global.utill;

public class TokenUtils {

	private TokenUtils() {
		throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다");
	}

	// 토큰 마스킹 유틸리티 메소드
	public static String maskToken(String token) {
		if (token == null || token.length() <= 8) {
			return "***마스킹된 토큰***";
		}

		int length = token.length();
		String prefix = token.substring(0, 4);
		String suffix = token.substring(length - 4);

		return prefix + "*".repeat(length - 8)
			+ suffix;
	}
}
