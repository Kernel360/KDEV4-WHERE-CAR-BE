package com.where_car.emulator.device.domain;

import org.springframework.stereotype.Component;

import com.where_car.emulator.device.application.dto.CarDto;
import com.where_car.emulator.device.application.dto.CycleInfoDto;
import com.where_car.emulator.device.domain.car.CarDevice;
import com.where_car.emulator.device.domain.car.CarIdentity;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;
import com.where_car.emulator.device.domain.cycle.CycleInfo;
import com.where_car.emulator.device.domain.event.CarStart;
import com.where_car.emulator.device.domain.event.CarStop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceFactory {

	public CarDto createCarStartDto(CarStart carStart) {
		return getCarDto(
			carStart.getCarIdentity(),
			carStart.getCarDevice(),
			carStart.getOnTime(),
			carStart.getOffTime(),
			carStart.getCycleInfo()
		);
	}

	public CarDto createCarStopDto(CarStop carStop) {
		return getCarDto(carStop.getCarIdentity(),
			carStop.getCarDevice(),
			carStop.getOnTime(),
			carStop.getOffTime(),
			carStop.getCycleInfo()
		);
	}

	public CycleInfoDto createCycleInfoDto(CycleInfo cycleInfo) {
		return CycleInfoDto.builder()
			.mdn(cycleInfo.getCarIdentity().getMdn())
			.tid(cycleInfo.getCarDevice().getTid())
			.mid(cycleInfo.getCarDevice().getMid())
			.pv(cycleInfo.getCarDevice().getPv())
			.did(cycleInfo.getCarDevice().getDid())
			.oTime(cycleInfo.getOTime())
			.cCnt(cycleInfo.getCCnt())
			.cList(cycleInfo.getCList())
			.build();
	}

	private CarDto getCarDto(CarIdentity carIdentity, CarDevice carDevice, String onTime, String offTime,
		CarCycleInfo cycleInfo) {
		return CarDto.builder()
			.mdn(carIdentity.getMdn())
			.tid(carDevice.getTid())
			.mid(carDevice.getMid())
			.pv(carDevice.getPv())
			.did(carDevice.getDid())
			.onTime(onTime)
			.offTime(offTime)
			.gcd(cycleInfo.getGcd())
			.lat(cycleInfo.getLat())
			.lon(cycleInfo.getLon())
			.ang(cycleInfo.getAng())
			.spd(cycleInfo.getSpd())
			.sum(cycleInfo.getSum())
			.build();
	}
}
