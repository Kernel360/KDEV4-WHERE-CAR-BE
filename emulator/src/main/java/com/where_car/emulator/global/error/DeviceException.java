package com.where_car.emulator.global.error;

import lombok.Getter;

@Getter
public class DeviceException extends RuntimeException {

  private final DeviceErrorCode errorCode;

  public DeviceException(DeviceErrorCode errorCode, Throwable cause) {
    super(errorCode.getMessage(), cause);
    this.errorCode = errorCode;
  }
}
