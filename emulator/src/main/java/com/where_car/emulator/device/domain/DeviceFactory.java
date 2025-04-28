package com.where_car.emulator.device.domain;

import org.springframework.stereotype.Component;

import com.where_car.emulator.device.application.dto.CarRequest;
import com.where_car.emulator.device.application.dto.CycleInfoRequest;
import com.where_car.emulator.device.domain.car.CarDevice;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;
import com.where_car.emulator.device.domain.cycle.CycleInfo;
import com.where_car.emulator.device.domain.event.CarStart;
import com.where_car.emulator.device.domain.event.CarStop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeviceFactory {

	public CycleInfoRequest createCycleInfoRequest(CycleInfo cycleInfo) {
		return CycleInfoRequest.builder()
			.mdn(cycleInfo.getMdn())
			.tid(cycleInfo.getCarDevice().getTid())
			.mid(cycleInfo.getCarDevice().getMid())
			.pv(cycleInfo.getCarDevice().getPv())
			.did(cycleInfo.getCarDevice().getDid())
			.oTime(cycleInfo.getOTime())
			.cCnt(cycleInfo.getCCnt())
			.cList(cycleInfo.getCList())
			.build();
	}

	public CarCycleInfo createCarCycleInfoRequest(CarCycleInfo carCycleInfo) {
		return CarCycleInfo.builder()
			.sec(carCycleInfo.getSec())
			.gcd(carCycleInfo.getGcd())
			.lat(carCycleInfo.getLat())
			.lon(carCycleInfo.getLon())
			.ang(carCycleInfo.getAng())
			.spd(carCycleInfo.getSpd())
			.sum(carCycleInfo.getSum())
			.bat(carCycleInfo.getBat())
			.build();
	}

	public CarRequest createCarStartRequest(CarStart carStart) {
		return createCarRequest(
			carStart.getMdn(),
			carStart.getCarDevice(),
			carStart.getOnTime(),
			carStart.getOffTime(),
			carStart.getCycleInfo()
		);
	}

	public CarRequest createCarStopRequest(CarStop carStop) {
		return createCarRequest(
			carStop.getMdn(),
			carStop.getCarDevice(),
			carStop.getOnTime(),
			carStop.getOffTime(),
			carStop.getCycleInfo()
		);
	}

	private CarRequest createCarRequest(String mdn, CarDevice carDevice, String onTime, String offTime,
		CarCycleInfo cycleInfo) {
		return CarRequest.builder()
			.mdn(mdn)
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
