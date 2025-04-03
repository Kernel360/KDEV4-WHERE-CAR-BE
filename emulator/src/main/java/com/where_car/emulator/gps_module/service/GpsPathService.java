package com.where_car.emulator.gps_module.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * GPX 파일을 처리하는 서비스 클래스입니다.
 */
@Slf4j
@Service
public class GpsPathService {

  private final ResourceLoader resourceLoader;

  public GpsPathService(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
  }

  /**
   * 어플리케이션 동작 중에 사용될 랜덤 GPX 파일을 반환합니다.
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

  /**
   * 주어진 GPX 파일을 파싱하여 모든 `trkpt` 요소를 리스트로 반환합니다.
   * @param gpxFile GPX 파일 리소스
   * @return `trkpt` 요소 리스트
   */
  public List<Element> parseGpxFile(Resource gpxFile) {
    List<Element> trkptList = new ArrayList<>();
    try (InputStream inputStream = gpxFile.getInputStream()) {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(inputStream);
      doc.getDocumentElement().normalize();
      NodeList nodeList = doc.getElementsByTagName("trkpt");
      for (int i = 0; i < nodeList.getLength(); i++) {
        trkptList.add((Element) nodeList.item(i));
      }
    } catch (Exception e) {
      log.error("GPX 파일을 파싱하는 중 오류가 발생했습니다.", e);
    }
    return trkptList;
  }

  /**
   * 주어진 GPX 파일에서 첫 번째와 두번째 `trkpt` 요소를 반환합니다.
   * @param gpxFile GPX 파일 리소스
   * @return 첫 번째와 두번째 `trkpt` 요소
   */
  public List<Element> getFirstTrkpt(Resource gpxFile) {
    List<Element> trkptList = parseGpxFile(gpxFile);
    List<Element> filteredList = new ArrayList<>();
    filteredList.add(trkptList.isEmpty() ? null : trkptList.get(0));
    filteredList.add(trkptList.isEmpty() ? null : trkptList.get(1));
    return filteredList;
  }

  /**
   * 주어진 GPX 파일에서 마지막 이전과 마지막 `trkpt` 요소를 반환합니다.
   * @param gpxFile GPX 파일 리소스
   * @return 마지막 `trkpt` 요소
   */
  public List<Element> getLastTrkpt(Resource gpxFile) {
    List<Element> trkptList = parseGpxFile(gpxFile);
    List<Element> filteredList = new ArrayList<>();
    filteredList.add(trkptList.isEmpty() ? null : trkptList.get(trkptList.size() - 2));
    filteredList.add(trkptList.isEmpty() ? null : trkptList.get(trkptList.size() - 1));
    return filteredList;
  }

  /**
   * 주어진 GPX 파일에서 모든 `trkpt` 요소를 리스트로 반환합니다.
   * @param gpxFile GPX 파일 리소스
   * @return 모든 `trkpt` 요소 리스트
   */
  public List<Element> getAllTrkpts(Resource gpxFile) {
    return parseGpxFile(gpxFile);
  }
}