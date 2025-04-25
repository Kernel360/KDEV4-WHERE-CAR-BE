package com.where_car.emulator.device.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.where_car.emulator.device.application.dto.CarRequest;
import com.where_car.emulator.device.application.dto.CycleInfoRequest;
import com.where_car.emulator.device.domain.cycle.CarCycleInfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceInfoService {

  private final DeviceEventFactory deviceEventFactory;
  private final DeviceEventExecutor deviceEventExecutor;

  @Getter
  private List<CarCycleInfo> carCycleInfoList = new ArrayList<>();

  public void generateAndSendCycleInfo() {
    CycleInfoRequest cycleInfoRequest = deviceEventFactory.generateCycleInfo(carCycleInfoList);
    deviceEventExecutor.sendCycleInfo(cycleInfoRequest);
    carCycleInfoList.clear();
  }

  public void generateAndSendCarCycleInfo() {
    CarCycleInfo carCycleInfo = deviceEventFactory.generateCarCycleInfo();
    carCycleInfoList.add(carCycleInfo);
  }

  public void generateAndSendCarStart() {
    CarRequest generateCarStart = deviceEventFactory.generateCarStart();
    deviceEventExecutor.sendCarStart(generateCarStart);
  }

  public void generateAndSendCarStop() {
    CarRequest carStopDto = deviceEventFactory.generateCarStop();
    deviceEventExecutor.sendCarStop(carStopDto);
  }
}

