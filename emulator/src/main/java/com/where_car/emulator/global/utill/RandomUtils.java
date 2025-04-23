package com.where_car.emulator.global.utill;

import java.util.Random;

public class RandomUtils {
	
	private static final Random RANDOM = new Random();

	private RandomUtils() {
		throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다");
	}

	public static int generateRandomBatteryValue() {
		double probability = RANDOM.nextDouble();

		if (probability < 0.8) {
			// 80% 확률로 12v ~ 15v 사이의 값
			return RANDOM.nextInt(4) + 12;
		} else if (probability < 0.95) {
			// 15% 확률로 10v ~ 12v 사이의 값
			return RANDOM.nextInt(3) + 10;
		} else {
			// 5% 확률로 8v ~ 10v 사이의 값
			return RANDOM.nextInt(3) + 8;
		}
	}
}
