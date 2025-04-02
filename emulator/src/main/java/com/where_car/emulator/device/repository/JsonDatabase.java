package com.where_car.emulator.device.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
  public void initialize() throws IOException {
    if (!file.exists()) {
      boolean isFileCreated = file.createNewFile();
      if (isFileCreated) {
        CarIdentity carIdentity = new CarIdentity();
        carIdentity.setMdn(mdn);
        carIdentity.setVrp(vrp);
        carIdentity.setTotalDistance(0);
        objectMapper.writeValue(file, List.of(carIdentity));
      }
    }
  }

  public List<CarIdentity> readData() throws IOException {
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

  public void writeData(List<CarIdentity> data) throws IOException {
    try {
      objectMapper.writeValue(file, data);
    } catch (DatabindException e) {
      throw new IOException("Error writing data to file", e);
    }
  }

  public void createCarIdentity(CarIdentity CarIdentity) throws IOException {
    List<CarIdentity> data = readData();
    data.add(CarIdentity);
    writeData(data);
  }

  public Optional<CarIdentity> getCarIdentityByMdn(String mdn) throws IOException {
    List<CarIdentity> data = readData();
    return data.stream().filter(car -> car.getMdn().equals(mdn)).findFirst();
  }

  public void updateCarIdentity(CarIdentity updatedCarIdentity) throws IOException {
    List<CarIdentity> data = readData();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).getMdn().equals(updatedCarIdentity.getMdn())) {
        data.set(i, updatedCarIdentity);
        writeData(data);
        return;
      }
    }
    throw new IOException("CarIdentity with MDN " + updatedCarIdentity.getMdn() + " not found");
  }

  public void deleteCarIdentityByMdn(String mdn) throws IOException {
    List<CarIdentity> data = readData();
    data.removeIf(car -> car.getMdn().equals(mdn));
    writeData(data);
  }
}