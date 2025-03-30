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

  @GetMapping("/dashboard")
  public String dashboard(Model model) {
    model.addAttribute("deviceStatus", deviceService.fetchDeviceStatus());
    model.addAttribute("carIdentity", deviceService.fetchCarIdentity());
    return "dashboard";
  }

  @PostMapping("/device/activate")
  public String schedulerActive() {
    deviceService.activateScheduler();
    return "redirect:/dashboard";
  }
}
