package com.where_car.emulator.device.controller;

import com.where_car.emulator.device.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeviceWebController {

  private final DeviceService deviceService;

  public DeviceWebController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  /**
   * <pre>
   *   에뮬레이터 대시보드
   *   tailwindcss를 사용하여 대시보드 UI를 구성 했습니다.
   * </pre>
   * @author Changil.kim
   * @version 1.0
   * @since 2025-03-30
   */
  @GetMapping("/dashboard")
  public String deviceDashboard(Model model) {
    model.addAttribute("deviceStatus", deviceService.isDeviceStatus());
    model.addAttribute("carIdentity", deviceService.fetchCarIdentity());
    return "dashboard";
  }

  /**
   * <pre>
   *   에뮬레이터 활성화/비활성화
   *   deviceStatus를 통해 스케줄러를 시작/정지 합니다.
   * </pre>
   * @author Changil.kim
   * @version 1.0
   * @since 2025-03-30
   */
  @PostMapping("/device/active")
  public String deviceActive() {
    deviceService.deviceActivateScheduler();
    return "redirect:/dashboard";
  }
}
