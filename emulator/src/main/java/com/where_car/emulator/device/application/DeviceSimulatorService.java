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
public class DeviceSimulatorService {

  private final SimulationEventFactory simulationEventFactory;
  private final DeviceEventExecutor deviceEventExecutor;

  @Getter
  private List<CarCycleInfo> carCycleInfoList = new ArrayList<>();

  public void generateAndSendCycleInfo() {
    CycleInfoRequest cycleInfoRequest = simulationEventFactory.generateCycleInfo(carCycleInfoList);
    deviceEventExecutor.sendCycleInfo(cycleInfoRequest);
    carCycleInfoList.clear();
  }

  public void generateAndSendCarCycleInfo() {
    CarCycleInfo carCycleInfo = simulationEventFactory.generateCarCycleInfo();
    carCycleInfoList.add(carCycleInfo);
  }

  public void generateAndSendCarStart() {
    CarRequest generateCarStart = simulationEventFactory.generateCarStart();
    deviceEventExecutor.sendCarStart(generateCarStart);
  }

  public void generateAndSendCarStop() {
    CarRequest carStopDto = simulationEventFactory.generateCarStop();
    deviceEventExecutor.sendCarStop(carStopDto);
  }
}

