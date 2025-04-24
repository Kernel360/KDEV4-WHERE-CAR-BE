package com.where_car.emulator.device.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarCycleInfoRequest {

	private String sec;
	private String gcd;
	private String lat;
	private String lon;
	private String ang;
	private String spd;
	private String sum;
	private String bat;
}
