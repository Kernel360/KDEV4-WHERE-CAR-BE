package com.where_car.emulator.device.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.where_car.emulator.device.application.DeviceRealTimeService;
import com.where_car.emulator.device.application.DeviceService;
import com.where_car.emulator.device.application.dto.LocationRequest;
import com.where_car.emulator.device.application.dto.TrackingRequest;
import com.where_car.emulator.global.error.DeviceException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 *   에뮬레이터 대시보드 및 상태 변경을 위한 웹 컨트롤러
 *   v0를 사용해서 tailwindcss를 사용해서 웹 대시보드를 구현했습니다.
 * </pre>
 *
 * @since 2025-03-30
 * @version 1.0
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class DeviceWebController {

  private final DeviceService deviceService;
  private final DeviceRealTimeService deviceRealTimeService;

  @GetMapping("/")
  public String redirectToDashboard() {
    return "redirect:/dashboard";
  }

  @GetMapping("/dashboard")
  public String deviceShowDashboard(Model model) {
    model.addAttribute("deviceStatus", deviceService.getDeviceStatus());
    model.addAttribute("carIdentity", deviceService.getCarIdentity());

    LocationRequest locationInfo = deviceService.getLocationInfo();
    model.addAttribute("departure", locationInfo.getDeparture());
    model.addAttribute("destination", locationInfo.getDestination());
    
    return "dashboard";
  }

  @PostMapping("/device/simulation/toggle")
  public String deviceSimulationToggle() {
    deviceService.toggleDeviceSimulation();
    return "redirect:/dashboard";
  }

  /**
   * 주기적 위치 정보 전송 메서드
   * 1초마다 수집된 위도/경도 정보 60개를 모아서 서비스에 전달
   */
  @PostMapping("/device/realtime/cycleInfo")
  public ResponseEntity<String> deviceRealTimeCycleInfo(@RequestBody List<TrackingRequest> trackingLocationList) {
    deviceRealTimeService.generateAndSendCycleInfo(trackingLocationList);
    return ResponseEntity.ok("success");
  }

  /**
   * 차량 시동 켜기 메서드
   * 현재 시간을 시작 시간으로 설정하여 서비스에 전달
   */
  @PostMapping("/device/realtime/on")
  public ResponseEntity<String> deviceRealTimeTracking(@RequestBody TrackingRequest trackingRequest) {
    deviceRealTimeService.generateAndSendCarStart(trackingRequest);
    return ResponseEntity.ok("success");
  }

  /**
   * 차량 시동 끄기 메서드
   * 저장된 시작 시간과 현재 종료 시간을 서비스에 전달
   */
  @PostMapping("/device/realtime/off")
  public ResponseEntity<String> deviceRealTimeUntracking(@RequestBody TrackingRequest trackingRequest) {
    deviceRealTimeService.generateAndSendCarStop(trackingRequest);
    return ResponseEntity.ok("success");
  }

  @ExceptionHandler(DeviceException.class)
  public String handleDeviceException(DeviceException e, Model model) {
    model.addAttribute("errorCode", e.getErrorCode().getCode());
    model.addAttribute("errorMessage", e.getErrorCode().getMessage());
    model.addAttribute("exception", e.getClass().getName());
    return "error/500";
  }
}
