package com.where_car.emulator.device.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {

	private String mdn;
	private String tid;
	private String mid;
	private String pv;
	private String did;
	private String dFWVer;
}
