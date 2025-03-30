package com.where_car.emulator.device.controller;

import com.where_car.emulator.device.dto.CarStartDto;
import com.where_car.emulator.device.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
public class DeviceApiController {

  private final DeviceService deviceService;

  public DeviceApiController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  @GetMapping("/start")
  public ResponseEntity<Object> deviceGetStartInfo () {
    if (deviceService.isDeviceStatus()) {
      CarStartDto carStart = deviceService.sendCarStart();
      return ResponseEntity.ok(carStart);
    } else {
      return ResponseEntity.status(400).body("에뮬레이터가 동작하고 있지 않습니다.");
    }
  }
}
