package com.where_car.emulator.gps_module.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class GpsPathService {

  private final ResourceLoader resourceLoader;

  public GpsPathService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  /**
   * -- GETTER --
   *  어플리케이션 동작 중에 사용될 랜덤 GPX 파일을 반환합니다.
   *
   * @return 랜덤으로 선택된 GPX 파일
   */
  @Getter
  private Resource randomGpxFile;

  /**
   * 서비스가 초기화될 때 `resources/gpx` 폴더에서 랜덤으로 GPX 파일을 선택합니다.
   * @throws IOException 파일을 읽는 중 오류가 발생할 경우
   */
  @PostConstruct
  public void init() throws IOException {
    this.randomGpxFile = getRandomGpxFile("gpx");
  }

  /**
   * 지정된 폴더에서 랜덤으로 GPX 파일을 선택합니다.
   * @param folderPath GPX 파일이 위치한 폴더 경로
   * @return 랜덤으로 선택된 GPX 파일
   * @throws IOException 파일을 읽는 중 오류가 발생할 경우
   */
  public Resource getRandomGpxFile(String folderPath) throws IOException {
    Resource resource = resourceLoader.getResource("classpath:" + folderPath);
    List<Resource> files = Files.walk(Paths.get(resource.getURI()))
        .filter(Files::isRegularFile)
        .map(path -> resourceLoader.getResource("classpath:" + folderPath + "/" + path.getFileName().toString()))
        .toList();

    if (files.isEmpty()) {
      throw new IOException("지정된 폴더에 파일이 없습니다.");
    }

    Random random = new Random();
    return files.get(random.nextInt(files.size()));
  }
}