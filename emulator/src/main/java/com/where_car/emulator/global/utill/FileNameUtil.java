package com.where_car.emulator.global.utill;

import org.springframework.stereotype.Component;

@Component
public class FileNameUtil {

  public static String[] extractLocations(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return new String[0];
    }

    // 파일 확장자 제거
    int dotIndex = fileName.lastIndexOf('.');
    if (dotIndex != -1) {
      fileName = fileName.substring(0, dotIndex);
    }

    // "지역-to-지역" 형식의 문자열만 추출
    String[] locations = fileName.split("-to-");
    if (locations.length == 2) {
      locations[0] = extractRegion(locations[0]);
      locations[1] = extractRegion(locations[1]);
      return locations;
    }

    return new String[0];
  }

  private static String extractRegion(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    // "지역-문자열" 또는 "지역_문자열" 형식에서 "지역"만 추출
    int hyphenIndex = str.indexOf('-');
    int underscoreIndex = str.indexOf('_');
    int endIndex = (hyphenIndex != -1) ? hyphenIndex : (underscoreIndex != -1) ? underscoreIndex : str.length();
    str = str.substring(0, endIndex);
    return capitalizeFirstLetter(str);
  }

  private static String capitalizeFirstLetter(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
}
