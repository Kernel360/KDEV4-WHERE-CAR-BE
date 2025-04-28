package com.where_car.emulator.global.constants;

import java.time.format.DateTimeFormatter;

public final class DateConstant {

  private DateConstant() {
  }

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
  public static final DateTimeFormatter DATE_TIME_MINUTE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
}
