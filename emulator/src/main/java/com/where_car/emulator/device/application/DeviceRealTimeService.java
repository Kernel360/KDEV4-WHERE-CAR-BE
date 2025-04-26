package com.where_car.emulator.device.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.where_car.emulator.device.application.dto.CarRequest;
import com.where_car.emulator.device.application.dto.CycleInfoRequest;
import com.where_car.emulator.device.application.dto.TrackingRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceRealTimeService {

  private final RealTimeEventFactory realTimeEventFactory;
  private final DeviceEventExecutor deviceEventExecutor;

  public void generateAndSendCycleInfo(List<TrackingRequest> trackingLocationList) {
    CycleInfoRequest cycleInfoRequest = realTimeEventFactory.generateCycleInfo(trackingLocationList);
    deviceEventExecutor.sendCycleInfo(cycleInfoRequest);
  }

  public void generateAndSendCarStart(TrackingRequest trackingRequest) {
    CarRequest generateCarStart = realTimeEventFactory.generateCarStart(trackingRequest);
    deviceEventExecutor.sendCarStart(generateCarStart);
  }

  public void generateAndSendCarStop(TrackingRequest trackingRequest) {
    CarRequest carStopDto = realTimeEventFactory.generateCarStop(trackingRequest);
    deviceEventExecutor.sendCarStop(carStopDto);
  }
}

