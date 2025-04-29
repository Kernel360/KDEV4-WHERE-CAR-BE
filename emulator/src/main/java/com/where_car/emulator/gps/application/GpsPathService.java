package com.where_car.emulator.gps.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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
   * 어플리케이션 동작 중에 사용될 GPX 파일을 반환합니다.
   */
  @Getter
  private Resource selectGpxFile;
  @Value("${emulator.device.gpx-file}")
  private String selectGpxFileName;

  /**
   * 서비스가 초기화될 때 기본 GPX 파일을 선택합니다.
   * @throws IOException 파일을 읽는 중 오류가 발생할 경우
   */
  @PostConstruct
  public void init() throws IOException {
    // 기본 GPX 파일 지정 (예: default.gpx)
    this.selectGpxFile = getGpxFile("gpx", selectGpxFileName);
  }

  /**
   * 지정된 폴더에서 특정 GPX 파일을 로드합니다.
   * @param folderPath GPX 파일이 위치한 폴더 경로
   * @param fileName 로드할 GPX 파일의 이름
   * @return 지정된 GPX 파일 리소스
   * @throws IOException 파일을 읽는 중 오류가 발생할 경우
   */
  public Resource getGpxFile(String folderPath, String fileName) throws IOException {
    Resource resource = resourceLoader.getResource("classpath:" + folderPath + "/" + fileName);
    
    if (!resource.exists()) {
      throw new IOException("지정된 파일을 찾을 수 없습니다: " + fileName);
    }
    
    return resource;
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
  public List<Element> getFirstGpx(Resource gpxFile) {
    List<Element> trkptList = parseGpxFile(gpxFile);
    List<Element> filteredList = new ArrayList<>();
    filteredList.add(trkptList.isEmpty() ? null : trkptList.get(0));
    filteredList.add(trkptList.isEmpty() ? null : trkptList.get(1));
    return filteredList;
  }

  /**
   * 주어진 GPX 파일에서 마��막 이전과 마지막 `trkpt` 요소를 반환합니다.
   * @param gpxFile GPX 파일 리소스
   * @return 마지막 `trkpt` 요소
   */
  public List<Element> getLastGpx(Resource gpxFile) {
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
  public List<Element> getAllGpx(Resource gpxFile) {
    return parseGpxFile(gpxFile);
  }
}
