package com.where_car.emulator.device.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
public class TokenDto {

	private TokenDto() {
		// 인스턴스화 방지
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		private String mdn;
		private String tid;
		private String mid;
		private String pv;
		private String did;
		private String dFWVer;
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Response {
		private String rstCd;
		private String rstMsg;
		private String mdn;
		private String token;
		private String exPeriod;
	}
}
