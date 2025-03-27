package com.wherecar.rest.controller;

import com.wherecar.rest.dto.RegisterCarRequest;
import com.wherecar.rest.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<String> registerCar(@RequestBody RegisterCarRequest registerCarRequest) {
        carService.registerCar(registerCarRequest);
        return ResponseEntity.ok("등록되었습니다.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCar(@PathVariable Long id, @RequestBody RegisterCarRequest registerCarRequest) {
        carService.updateCar(id, registerCarRequest);
        return ResponseEntity.ok("수정되었습니다.");
    }

}
