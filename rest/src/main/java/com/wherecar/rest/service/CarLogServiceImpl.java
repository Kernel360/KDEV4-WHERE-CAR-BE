package com.wherecar.rest.service;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.domain.CarLog;
import com.wherecar.rest.domain.CarStatus;
import com.wherecar.rest.dto.CarLogDetailResponse;
import com.wherecar.rest.dto.CarLogsResponse;
import com.wherecar.rest.repository.CarLogRepository;
import com.wherecar.rest.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CarLogServiceImpl implements CarLogService {

    private final CarRepository carRepository;
    private final CarLogRepository carLogRepository;

    private static final double METER_TO_KILOMETER = 1000.0;

    @Override
    @Transactional(readOnly = true)
    public List<CarLogsResponse> getCarLogs(int page, int size) {
        // Todo: 현재 사용자 정보 조회 (Admin, User에 따라 구분)
        // Todo: 현재 사용자의 Company 아이디 조회

        Long userCompanyId = null;

        PageRequest pageRequest = PageRequest.of(page,size);

        //Todo: (GPS) 데이터와 병합하여 처리 예정

        Page<Car> carPage = carRepository.findByCompanyId(userCompanyId, pageRequest);

        return carPage.stream().map(car -> CarLogsResponse.builder()
                        .carId(car.getId())
                        .mdn(car.getMdn())
                        .model(car.getModel())
                        .mileage(car.getMileage())
                        //Todo: 차량 현황 추가(GPS)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarLogDetailResponse> getCarLogsDetails(Long carId, int page, int size) {

        carRepository.findById(carId).orElseThrow(() -> new RuntimeException("차량을 찾을 수 없습니다."));

        PageRequest pageRequest = PageRequest.of(page,size);

        Page<CarLog> carLogPage = carLogRepository.findByCarId(carId, pageRequest);

        return carLogPage.stream().map(carLog -> CarLogDetailResponse.builder()
                        .LogId(carLog.getId())
                        .onTime(carLog.getOnTime())
                        .offTime(carLog.getOffTime())
                        .onMileage(carLog.getOnMileage())
                        .offMileage(carLog.getOffMileage())
                        .totalMileage((carLog.getOffMileage() - carLog.getOnMileage())/METER_TO_KILOMETER)
                        .driveType(carLog.getDriveType())
                        .carStatus(carLog.getOffMileage() != null ? CarStatus.STOPPED : CarStatus.RUNNING)
                        .description(carLog.getDescription())
                        .driver(carLog.getDriver())
                        .build())
                .collect(Collectors.toList());

    }


}
