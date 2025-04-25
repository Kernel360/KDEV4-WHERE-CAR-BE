package com.where_car.emulator.device.infrastructure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.where_car.emulator.device.domain.car.CarIdentity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JsonDatabase {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final File file;
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private String currentMdn = "";

  @Value("${wherecar.device.mdn}")
  private String mdn;

  @Value("${wherecar.device.vrp}")
  private String vrp;

  public JsonDatabase(@Value("${wherecar.db.storage-file-name}") String fileName) {
    String jarDir = Paths.get("").toAbsolutePath().toString();
    this.file = new File(jarDir, fileName);
  }

  @PostConstruct
  public void initialize() {
    try {
      this.currentMdn = mdn;
      if (!file.exists()) {
        // 파일이 없는 경우 새 파일 생성
        boolean isFileCreated = file.createNewFile();
        if (isFileCreated) {
          CarIdentity carIdentity = createData();
          objectMapper.writeValue(file, List.of(carIdentity));
          log.info("데이터베이스 파일 생성 및 MDN {} 초기 데이터 추가 완료", mdn);
        }
      } else {
        // 파일은 존재하지만 해당 MDN을 가진 데이터가 없는지 확인
        List<CarIdentity> existingData = readData();
        boolean mdnExists = existingData.stream()
            .anyMatch(car -> car.getMdn().equals(mdn));

        if (!mdnExists) {
          // MDN이 없으면 새 데이터 추가
          CarIdentity carIdentity = createData();
          existingData.add(carIdentity);
          writeData(existingData);
          log.info("MDN {}에 대한 데이터가 없어 새 데이터를 추가했습니다", mdn);
        }
      }
    } catch (IOException e) {
      log.error("데이터베이스 파일 초기화 실패", e);
      throw new IllegalArgumentException("데이터베이스 초기화 중 오류 발생", e);
    }
  }

  /**
   * MDN 변경 여부를 확인하고 변경되었으면 새 데이터를 생성합니다.
   */
  public void checkAndUpdateMdnChange() {
    if (!currentMdn.equals(mdn)) {
      log.info("MDN이 변경되었습니다: {} -> {}", currentMdn, mdn);
      List<CarIdentity> existingData = readData();
      
      boolean mdnExists = existingData.stream()
          .anyMatch(car -> car.getMdn().equals(mdn));
      
      if (!mdnExists) {
        // 새로운 MDN에 대한 데이터 생성
        CarIdentity carIdentity = createData();
        existingData.add(carIdentity);
        writeData(existingData);
        log.info("새 MDN {}에 대한 데이터를 추가했습니다", mdn);
      }
      
      // 현재 MDN 업데이트
      currentMdn = mdn;
    }
  }

  private CarIdentity createData() {
	  return new CarIdentity(mdn, vrp);
  }

  public List<CarIdentity> readData() {
    lock.readLock().lock();
    try {
      // MDN 변경 확인
      checkAndUpdateMdnChange();
      
      if (!file.exists()) {
        try {
          lock.readLock().unlock();
          lock.writeLock().lock();
          try {
            // 다시 체크 (다른 스레드가 파일을 생성했을 수 있음)
            if (!file.exists()) {
              boolean isFileCreated = file.createNewFile();
              if (isFileCreated) {
                objectMapper.writeValue(file, new ArrayList<>());
              }
            }
          } finally {
            lock.writeLock().unlock();
            lock.readLock().lock();
          }
        } catch (IOException e) {
          log.error("Failed to create new database file", e);
          return new ArrayList<>();
        }
      }
      try {
        List<CarIdentity> result = objectMapper.readValue(file, new TypeReference<>() {});
        return result != null ? result : new ArrayList<>();
      } catch (DatabindException e) {
        log.error("Error reading data from file", e);
        throw new IllegalArgumentException("데이터 변환 중 오류 발생", e);
      } catch (IOException e) {
        log.error("IO error reading data from file", e);
        throw new IllegalArgumentException("파일 읽기 중 오류 발생", e);
      }
    } finally {
      lock.readLock().unlock();
    }
  }

  public void writeData(List<CarIdentity> data) {
    Objects.requireNonNull(data, "데이터는 null이 될 수 없습니다");
    
    lock.writeLock().lock();
    try {
      objectMapper.writeValue(file, data);
    } catch (DatabindException e) {
      log.error("Error writing data to file", e);
      throw new IllegalArgumentException("데이터 변환 중 오류 발생", e);
    } catch (IOException e) {
      log.error("IO error writing data to file", e);
      throw new IllegalArgumentException("파일 쓰기 중 오류 발생", e);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public Optional<CarIdentity> getCarIdentityByMdn(String mdn) {
    Objects.requireNonNull(mdn, "MDN은 null이 될 수 없습니다");
    
    List<CarIdentity> data = readData();
    return data.stream().filter(car -> car.getMdn().equals(mdn)).findFirst();
  }

  public void updateCarIdentity(CarIdentity updatedCarIdentity) {
    Objects.requireNonNull(updatedCarIdentity, "CarIdentity는 null이 될 수 없습니다");
    
    List<CarIdentity> data = readData();
    boolean updated = false;
    
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getMdn().equals(updatedCarIdentity.getMdn())) {
        data.set(i, updatedCarIdentity);
        writeData(data);
        updated = true;
        break;
      }
    }
    
    if (!updated) {
      log.error("CarIdentity with MDN {} not found", updatedCarIdentity.getMdn());
      throw new IllegalArgumentException(String.format("MDN %s를 가진 CarIdentity를 찾을 수 없습니다", updatedCarIdentity.getMdn()));
    }
  }
}
