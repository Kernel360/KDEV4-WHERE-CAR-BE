package com.wherecar.rest.service;

import com.wherecar.rest.domain.Car;
import com.wherecar.rest.domain.CarLog;
import com.wherecar.rest.dto.CarLogDetailResponse;
import com.wherecar.rest.dto.CarLogsResponse;
import com.wherecar.rest.dto.CarLogsUpdateRequest;
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

    private static final Integer METER_TO_KILOMETER = 1000;


    // (운행일지 + 차량) 목록
    @Override
    @Transactional(readOnly = true)
    public List<CarLogsResponse> getCarLogs(int page, int size) {
        // Todo: 현재 사용자 정보 조회 (Admin, User에 따라 구분)
        // Todo: 현재 사용자의 Company 아이디 조회

        Long userCompanyId = null;

        PageRequest pageRequest = PageRequest.of(page,size);

        Page<CarLog> carLogPage = carLogRepository.findByCompanyId(userCompanyId, pageRequest);

        return carLogPage.stream().map(carLog -> CarLogsResponse.builder()
                        .LogId(carLog.getId())
                        .mdn(carLog.getCar().getMdn())
                        .onTime(carLog.getOnTime())
                        .offTime(carLog.getOffTime())
                        .onMileage(carLog.getOnMileage())
                        .offMileage(carLog.getOffMileage())
                        .driver(carLog.getDriver())
                        .driveType(carLog.getDriveType())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CarLogsResponse> getCarLogsByCarId(String mdn, int page, int size) {
        // Todo: 현재 사용자 정보 조회 (Admin, User에 따라 구분)
        // Todo: 현재 사용자의 Company 아이디 조회

        Long userCompanyId = null;

        PageRequest pageRequest = PageRequest.of(page,size);

        Page<CarLog> carLogPage = carLogRepository.findByCompanyIdAndCarId(userCompanyId, mdn, pageRequest);

        if (carLogPage.isEmpty()) {
            throw new RuntimeException("해당 차량의 운행일지를 찾을 수 없습니다.");
        }

        return carLogPage.stream().map(carLog -> CarLogsResponse.builder()
                        .LogId(carLog.getId())
                        .mdn(carLog.getCar().getMdn())
                        .onTime(carLog.getOnTime())
                        .offTime(carLog.getOffTime())
                        .onMileage(carLog.getOnMileage())
                        .offMileage(carLog.getOffMileage())
                        .driver(carLog.getDriver())
                        .driveType(carLog.getDriveType())
                        .build())
                .collect(Collectors.toList());

    }


    //운행일지 상세 정보
    @Override
    @Transactional(readOnly = true)
    public CarLogDetailResponse getCarLogsDetails(Long logId) {

        CarLog carLog = carLogRepository.findById(logId).orElseThrow(() -> new RuntimeException("해당 차량의 일지를 찾을 수 없습니다."));

        return CarLogDetailResponse.builder()
                .LogId(carLog.getId())
                .onTime(carLog.getOnTime())
                .offTime(carLog.getOffTime())
                .onMileage(carLog.getOnMileage())
                .offMileage(carLog.getOffMileage())
                .totalMileage((carLog.getOffMileage() - carLog.getOnMileage()) / METER_TO_KILOMETER)
                .driveType(carLog.getDriveType())
                .description(carLog.getDescription())
                .driver(carLog.getDriver())
                .build();

    }

    //운행일지 상세 정보 수정
    @Override
    public void updateCarLogDetails(Long id, CarLogsUpdateRequest carLogsUpdateRequest) {

        CarLog carLog = carLogRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 차량의 일지를 찾을 없습니다."));

        carLog.changeDescription(carLogsUpdateRequest.getDescription());
        carLog.changeDriver(carLogsUpdateRequest.getDriver());
        carLog.changeDriveType(carLogsUpdateRequest.getDriveType());

        carLogRepository.save(carLog);

    }

    //운행일지 상세 정보 삭제
    @Override
    public void deleteCarLogDetails(Long id) {

        if (!carLogRepository.existsById(id)) {
            throw new RuntimeException("해당 차량의 일지를 찾을 없습니다.");
        }
        carLogRepository.deleteById(id);

    }

}
