package com.wherecar.rest.car.presentation;

import com.wherecar.rest.car.application.CarService;
import com.wherecar.rest.car.application.dto.CarOverviewResponse;
import com.wherecar.rest.car.application.dto.CarRegisterRequest;
import com.wherecar.rest.car.application.dto.CarResponse;
import com.wherecar.rest.common.constants.PaginationConstants;
import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.security.aspect.RequiredPermission;
import com.wherecar.rest.user.domain.constant.PermissionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Todo: 권한 체크 추후 추가 예정
@Slf4j
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @RequiredPermission(PermissionType.PERM_VEHICLE_ADD)
    @PostMapping
    public ResponseEntity<BaseResponse<CarResponse>> CarCreate(HttpServletRequest request, @RequestBody @Valid CarRegisterRequest registerCarRequest) {
        Long companyId = (Long)request.getAttribute("companyId");
        CarResponse carResponse = carService.createCar(companyId, registerCarRequest);
        return BaseResponse.created(carResponse);
    }
    @RequiredPermission(PermissionType.PERM_VEHICLE_EDIT)
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<CarResponse>> CarUpdate(@PathVariable Long id, @RequestBody @Valid CarRegisterRequest registerCarRequest) {
        CarResponse carResponse = carService.updateCar(id, registerCarRequest);
        return BaseResponse.created(carResponse);
    }

    @RequiredPermission(PermissionType.PERM_VEHICLE_DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> CarDelete(@PathVariable Long id) {
        carService.deleteCar(id);
        return BaseResponse.ok();
    }

    @RequiredPermission(PermissionType.PERM_VEHICLE_VIEW)
    @GetMapping
    public ResponseEntity<BaseResponse<List<CarResponse>>> CarsGetAll(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_SIZE) int size) {

        Long companyId = (Long)request.getAttribute("companyId");
        List<CarResponse> cars = carService.getAllCars(companyId, page, size);
        log.info("CarsGetAll cars size {}, companyId {}", cars.size(), companyId);
        return BaseResponse.ok(cars);
    }

    @RequiredPermission(PermissionType.PERM_VEHICLE_VIEW)
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CarResponse>> CarGetDetails(@PathVariable Long id) {
        CarResponse carResponse = carService.getCarDetails(id);
        return BaseResponse.ok(carResponse);
    }


    //정보별 차량 수 반환
    @GetMapping("/overview")
    public ResponseEntity<BaseResponse<CarOverviewResponse>> CarGetOverview(HttpServletRequest request) {
        Long companyId = (Long)request.getAttribute("companyId");
        CarOverviewResponse info = carService.getCarOverview(companyId);
        return BaseResponse.ok(info);
    }

}
