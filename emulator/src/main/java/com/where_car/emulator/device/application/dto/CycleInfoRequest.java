package com.where_car.emulator.device.application.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;

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
public class CycleInfoRequest {

	private String mdn;
	private String tid;
	private String mid;
	private String pv;
	private String did;
	@JsonProperty("oTime")
	private String oTime;
	@JsonProperty("cCnt")
	private String cCnt;
	@JsonProperty("cList")
	private List<CarCycleInfo> cList;
}
