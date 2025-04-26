package com.where_car.emulator.device.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {

	private String rstCd;
	private String rstMsg;
	private String mdn;
	private String token;
	private String exPeriod;
}
