package com.where_car.emulator.global.constants;

import java.time.format.DateTimeFormatter;

public class DateConstant {

  private DateConstant() {}

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
}
