package com.where_car.emulator.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeviceErrorCode {

  // 공통 예외
  INTERNAL_SERVER_ERROR(500, "E001", "서버 내부 오류가 발생했습니다."),

  // 디바이스 예외
  DEVICE_START_FAILED(400, "D001", "차량 시동을 켜는 데 실패했습니다."),
  DEVICE_STOP_FAILED(400, "D002", "차량 시동을 끄는 데 실패했습니다."),
  SCHEDULER_START_FAILED(400, "D003", "스케줄러 시작에 실패했습니다."),
  REQUEST_FAILED(500, "D004", "서버 요청에 실패했습니다.");

  private final int status;
  private final String code;
  private final String message;
}
