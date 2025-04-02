package com.where_car.emulator.device.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.where_car.emulator.device.domain.CarEntity;
import com.where_car.emulator.device.domain.common.CarIdentity;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class JsonDatabase {

  CarIdentity carIdentity;

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final File file;

  public JsonDatabase(@Value("${wherecar.db.file-name}") String fileName) {
    String jarDir = Paths.get("").toAbsolutePath().toString();
    this.file = new File(jarDir, fileName);
  }

  @PostConstruct
  public void initialize() throws IOException {
    if (!file.exists()) {
      boolean isFileCreated = file.createNewFile();
      if (isFileCreated) {
        CarEntity initialCarEntity = CarEntity.builder()
            .carIdentity(carIdentity)
            .totalDistance(0)
            .build();
        objectMapper.writeValue(file, List.of(initialCarEntity));
      }
    }
  }

  public List<CarEntity> readData() throws IOException {
    if (!file.exists()) {
      boolean isFileCreated = file.createNewFile();
      if (isFileCreated) {
        objectMapper.writeValue(file, List.of());
      }
    }
    try {
      return objectMapper.readValue(file, new TypeReference<>() {});
    } catch (DatabindException e) {
      throw new IOException("Error reading data from file", e);
    }
  }

  public void writeData(List<CarEntity> data) throws IOException {
    try {
      objectMapper.writeValue(file, data);
    } catch (DatabindException e) {
      throw new IOException("Error writing data to file", e);
    }
  }

  public void createCarEntity(CarEntity carEntity) throws IOException {
    List<CarEntity> data = readData();
    data.add(carEntity);
    writeData(data);
  }

  public Optional<CarEntity> getCarEntityByMdn(String mdn) throws IOException {
    List<CarEntity> data = readData();
    return data.stream().filter(car -> car.getCarIdentity().getMdn().equals(mdn)).findFirst();
  }

  public void updateCarEntity(CarEntity updatedCarEntity) throws IOException {
    List<CarEntity> data = readData();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getCarIdentity().getMdn().equals(updatedCarEntity.getCarIdentity().getMdn())) {
        data.set(i, updatedCarEntity);
        writeData(data);
        return;
      }
    }
    throw new IOException("CarEntity with MDN " + updatedCarEntity.getCarIdentity().getMdn() + " not found");
  }

  public void deleteCarEntityByMdn(String mdn) throws IOException {
    List<CarEntity> data = readData();
    data.removeIf(car -> car.getCarIdentity().getMdn().equals(mdn));
    writeData(data);
  }
}