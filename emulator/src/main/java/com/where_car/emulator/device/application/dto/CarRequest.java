package com.where_car.emulator.device.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class CarRequest {

	private String mdn;
	private String tid;
	private String mid;
	private String pv;
	private String did;
	@JsonProperty("onTime")
	private String onTime;
	@JsonProperty("offTime")
	private String offTime;
	private String gcd;
	private String lat;
	private String lon;
	private String ang;
	private String spd;
	private String sum;
}
