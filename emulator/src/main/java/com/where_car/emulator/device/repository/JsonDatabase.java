package com.where_car.emulator.device.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.where_car.emulator.device.domain.common.CarIdentity;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class JsonDatabase {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final File file;

  @Value("${wherecar.emulator.car-mdn}")
  private String mdn;

  @Value("${wherecar.emulator.car-vrp}")
  private String vrp;

  public JsonDatabase(@Value("${wherecar.db.file-name}") String fileName) {
    String jarDir = Paths.get("").toAbsolutePath().toString();
    this.file = new File(jarDir, fileName);
  }

  @PostConstruct
  public void initialize() {
    try {
      if (!file.exists()) {
        boolean isFileCreated = file.createNewFile();
        if (isFileCreated) {
          CarIdentity carIdentity = new CarIdentity();
          carIdentity.setMdn(mdn);
          carIdentity.setVrp(vrp);
          carIdentity.setTotalDistance(String.valueOf(0));
          objectMapper.writeValue(file, List.of(carIdentity));
        }
      }
    } catch (IOException e) {
      log.error("Failed to initialize database file", e);
    }
  }

  public List<CarIdentity> readData() {
    if (!file.exists()) {
      try {
        boolean isFileCreated = file.createNewFile();
        if (isFileCreated) {
          objectMapper.writeValue(file, List.of());
        }
      } catch (IOException e) {
        log.error("Failed to create new database file", e);
        return Collections.emptyList();
      }
    }
    try {
      return objectMapper.readValue(file, new TypeReference<>() {});
    } catch (DatabindException e) {
      log.error("Error reading data from file", e);
      return Collections.emptyList();
    } catch (IOException e) {
      log.error("IO error reading data from file", e);
      return Collections.emptyList();
    }
  }

  public void writeData(List<CarIdentity> data) {
    try {
      objectMapper.writeValue(file, data);
    } catch (DatabindException e) {
      log.error("Error writing data to file", e);
    } catch (IOException e) {
      log.error("IO error writing data to file", e);
    }
  }

  public void createCarIdentity(CarIdentity carIdentity) {
    List<CarIdentity> data = readData();
    data.add(carIdentity);
    writeData(data);
  }

  public Optional<CarIdentity> getCarIdentityByMdn(String mdn) {
    List<CarIdentity> data = readData();
    return data.stream().filter(car -> car.getMdn().equals(mdn)).findFirst();
  }

  public void updateCarIdentity(CarIdentity updatedCarIdentity) {
    List<CarIdentity> data = readData();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getMdn().equals(updatedCarIdentity.getMdn())) {
        data.set(i, updatedCarIdentity);
        writeData(data);
        return;
      }
    }
    log.error("CarIdentity with MDN {} not found", updatedCarIdentity.getMdn());
  }

  public void deleteCarIdentityByMdn(String mdn) {
    List<CarIdentity> data = readData();
    data.removeIf(car -> car.getMdn().equals(mdn));
    writeData(data);
  }
}