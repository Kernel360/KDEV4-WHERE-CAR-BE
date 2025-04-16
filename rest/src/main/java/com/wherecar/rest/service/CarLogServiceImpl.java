package com.wherecar.rest.service;

import com.wherecar.rest.domain.CarLog;
import com.wherecar.rest.dto.CarLogDetailResponse;
import com.wherecar.rest.dto.CarLogsResponse;
import com.wherecar.rest.dto.CarLogsUpdateRequest;
import com.wherecar.rest.dto.MonthlyMileage;
import com.wherecar.rest.repository.CarLogRepository;
import com.wherecar.rest.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CarLogServiceImpl implements CarLogService {

    private final CarRepository carRepository;
    private final CarLogRepository carLogRepository;

    private static final Integer METER_TO_KILOMETER = 1000;

    //차량 목록 조회(필터 추가)
    @Override
    @Transactional(readOnly = true)
    public Page<CarLogsResponse> getCarLogsFiltered(
            Long companyId,
            String mdn,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int page,
            int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<CarLog> logs = carLogRepository.findCarLogsFiltered(companyId, mdn, startTime, endTime, pageRequest);

        return logs.map(carLog -> CarLogsResponse.builder()
                .logId(carLog.getId())
                .mdn(carLog.getMdn())
                .onTime(carLog.getOnTime())
                .offTime(carLog.getOffTime())
                .onMileage(carLog.getOnMileage())
                .offMileage(carLog.getOffMileage())
                .driver(carLog.getDriver())
                .driveType(carLog.getDriveType())
                .description(carLog.getDescription())
                .build()
        );
    }




    //운행일지 상세 정보
    @Override
    @Transactional(readOnly = true)
    public CarLogDetailResponse getCarLogsDetails(Long logId) {

        CarLog carLog = carLogRepository.findById(logId).orElseThrow(() -> new RuntimeException("해당 차량의 일지를 찾을 수 없습니다."));

        return CarLogDetailResponse.builder()
                .logId(carLog.getId())
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

    @Override
    public CarLogsResponse getAllCarLogsStatics(Long companyId) {

        List<String> mdns = carRepository.findMdnsByCompanyId(companyId);

        List<CarLog> logs = carLogRepository.findByMdnIn(mdns);

        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        // 당월 로그 필터링
        List<CarLog> currentMonthLogs = logs.stream()
                .filter(log -> {
                    LocalDateTime onTime = log.getOnTime();
                    return onTime != null
                            && onTime.getYear() == currentYear
                            && onTime.getMonthValue() == currentMonth;
                })
                .collect(Collectors.toList());

        // 로그 건수
        long count = currentMonthLogs.size();

        // 총 주행 거리 계산
        long totalMileage = currentMonthLogs.stream()
                .mapToLong(log -> log.getOffMileage() - log.getOnMileage())
                .sum();

        // 최근 6개월간 총 주행거리 계산
        Map<String, Integer> monthlyMileageMap = new LinkedHashMap<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate targetDate = now.minusMonths(i);
            int year = targetDate.getYear();
            int month = targetDate.getMonthValue();

            int mileage = logs.stream()
                    .filter(log -> {
                        LocalDateTime onTime = log.getOnTime();
                        return onTime != null
                                && onTime.getYear() == year
                                && onTime.getMonthValue() == month;
                    })
                    .mapToInt(log -> log.getOffMileage() - log.getOnMileage())
                    .sum();

            String key = String.format("%d-%02d", year, month);
            monthlyMileageMap.put(key, mileage);
        }

        List<MonthlyMileage> monthlyMileages = monthlyMileageMap.entrySet().stream()
                .map(entry -> new MonthlyMileage(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return CarLogsResponse.builder()
                .totalMileage((int) totalMileage)
                .carLogsCount(String.valueOf(count))
                .monthlyMileages(monthlyMileages)
                .build();
    }


}
