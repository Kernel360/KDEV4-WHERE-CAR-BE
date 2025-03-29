package com.where_car.emulator.device.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeviceWebController {

  @RequestMapping("/dashboard")
  public String home() {
    return "dashboard.html";
  }
}
