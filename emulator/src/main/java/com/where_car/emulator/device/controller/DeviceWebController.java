package com.where_car.emulator.device.controller;

import com.where_car.emulator.device.service.DeviceService;
import com.where_car.emulator.global.utill.FileNameUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <pre>
 *   에뮬레이터 대시보드 및 상태 변경을 위한 웹 컨트롤러
 *   v0를 사용해서 tailwindcss를 사용해서 웹 대시보드를 구현했습니다.
 * </pre>
 *
 * @since 2025-03-30
 * @author Changil.kim
 * @version 1.0
 */
@Controller
public class DeviceWebController {

  private final DeviceService deviceService;
  private final FileNameUtil fileNameUtil;

  public DeviceWebController(DeviceService deviceService, FileNameUtil fileNameUtil) {
    this.deviceService = deviceService;
    this.fileNameUtil = fileNameUtil;
  }

  /**
   * <pre>
   *   루트 주소를 입력하면 대시보드 페이지로 리다이렉트 합니다.
   * </pre>
   * @return 대시보드 페이지 템플릿 이름
   */
  @GetMapping("/")
  public String redirectToDashboard() {
    return "redirect:/dashboard";
  }

  /**
   * <pre>
   *   에뮬레이터 대시보드
   *   대시보드 페이지를 반환합니다.
   * </pre>
   *
   * @param model Spring MVC의 Model 객체
   * @return 대시보드 페이지 템플릿 이름
   */
  @GetMapping("/dashboard")
  public String deviceShowDashboard(Model model) {
    model.addAttribute("deviceStatus", deviceService.isDeviceStatus());
    model.addAttribute("carIdentity", deviceService.fetchCarIdentity());

    String fileName = deviceService.getFilename();
    if (fileName != null) {
      String[] locations = FileNameUtil.extractLocations(fileName);
      if (locations.length == 2) {
        model.addAttribute("departure", locations[0]);
        model.addAttribute("destination", locations[1]);
      }
    }
    
    return "dashboard";
  }

  private String capitalizeFirstLetter(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  /**
   * <pre>
   *   에뮬레이터 활성화/비활성화
   *   버튼 클릭 시 에뮬레이터를 활성화 또는 비활성화 합니다.
   * </pre>
   *
   * @return 대시보드 페이지로 리다이렉트
   */
  @PostMapping("/device/toggle")
  public String deviceUpdateStatus() {
    deviceService.toggleDevice();
    return "redirect:/dashboard";
  }
}
