package com.wherecar.rest.controller;

import com.wherecar.rest.constants.PaginationConstants;
import com.wherecar.rest.dto.CarResponse;
import com.wherecar.rest.dto.CarRegisterRequest;
import com.wherecar.rest.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<String> carRegister(@RequestBody CarRegisterRequest registerCarRequest) {
        carService.registerCar(registerCarRequest);
        return ResponseEntity.ok("등록되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> carUpdate(@PathVariable Long id, @RequestBody CarRegisterRequest registerCarRequest) {
        carService.updateCar(id, registerCarRequest);
        return ResponseEntity.ok("수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> carDelete(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> carsGetAll(
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size) {

        List<CarResponse> cars = carService.getAllCars(page, size);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> detailsGetCar(@PathVariable Long id) {
        CarResponse car = carService.getCarDetails(id);
        return ResponseEntity.ok(car);
    }

}
