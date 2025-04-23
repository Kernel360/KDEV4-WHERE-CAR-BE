package com.where_car.emulator.global.utill;

public class FileUtils {

  private FileUtils() {
    throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다");
  }

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

  public static String extractRegion(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    // "지역-문자열" 또는 "지역_문자열" 형식에서 "지역"만 추출
    int hyphenIndex = str.indexOf('-');
    int underscoreIndex = str.indexOf('_');
    int endIndex;
	  if ((hyphenIndex != -1)) {
		  endIndex = hyphenIndex;
	  } else {
		  if (underscoreIndex != -1)
			  endIndex = underscoreIndex;
		  else
			  endIndex = str.length();
	  }
	  str = str.substring(0, endIndex);
    return capitalizeFirstLetter(str);
  }

  public static String capitalizeFirstLetter(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }
}
