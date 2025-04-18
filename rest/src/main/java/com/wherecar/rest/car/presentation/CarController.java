package com.wherecar.rest.car.presentation;

import com.wherecar.rest.car.application.CarService;
import com.wherecar.rest.car.application.dto.CarOverviewResponse;
import com.wherecar.rest.car.application.dto.CarRegisterRequest;
import com.wherecar.rest.car.application.dto.CarResponse;
import com.wherecar.rest.common.constants.PaginationConstants;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Void> CarCreate(HttpServletRequest request, @RequestBody CarRegisterRequest registerCarRequest) {
        Long companyId = (Long)request.getAttribute("companyId");
        carService.createCar(companyId, registerCarRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> CarUpdate(@PathVariable Long id, @RequestBody CarRegisterRequest registerCarRequest) {
        carService.updateCar(id, registerCarRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> CarDelete(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> CarsGetAll(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size) {

        Long companyId = (Long)request.getAttribute("companyId");
        List<CarResponse> cars = carService.getAllCars(companyId, page, size);
        log.info("CarsGetAll cars size {}, companyId {}", cars.size(), companyId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> CarGetDetails(@PathVariable Long id) {
        CarResponse car = carService.getCarDetails(id);
        return ResponseEntity.ok(car);
    }

    //정보별 차량 수 반환
    @GetMapping("/overview")
    public ResponseEntity<CarOverviewResponse> CarGetOverview(HttpServletRequest request) {
        Long companyId = (Long)request.getAttribute("companyId");
        CarOverviewResponse info = carService.getCarOverview(companyId);
        return ResponseEntity.ok(info);
    }

}
