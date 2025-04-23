package com.wherecar.rest.carlog.application;

import com.wherecar.rest.carlog.application.dto.CarLogResponse;
import com.wherecar.rest.carlog.domain.CarLog;
import com.wherecar.rest.carlog.application.dto.CarLogsUpdateRequest;
import com.wherecar.rest.carlog.application.dto.MonthlyMileage;
import com.wherecar.rest.carlog.domain.CarLogFactory;
import com.wherecar.rest.carlog.infrastructure.CarLogReader;
import com.wherecar.rest.carlog.infrastructure.CarLogRepository;
import com.wherecar.rest.car.infrastructure.CarRepository;
import com.wherecar.rest.carlog.infrastructure.CarLogStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final CarLogReader carLogReader;
    private final CarLogStore carLogStore;
    private final CarLogFactory carLogFactory;

    //차량 목록 조회(필터 추가)
    @Override
    @Transactional(readOnly = true)
    public Page<CarLogResponse> getCarLogsFiltered(
            Long companyId,
            String mdn,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int page,
            int size
    ) {

        Page<CarLog> carLogs = carLogReader.getCarLogsFiltered(companyId, mdn, startTime, endTime, page, size);
        return carLogFactory.toCarLogsResponsePage(carLogs);

    }

    //운행일지 상세 정보
    @Override
    @Transactional(readOnly = true)
    public CarLogResponse getCarLogDetails(Long carLogId) {

        CarLog carLog = carLogReader.getCarLogById(carLogId);
        return carLogFactory.toCarLogResponse(carLog);

    }

    //운행일지 상세 정보 수정
    @Override
    public CarLogResponse updateCarLogDetails(Long carLogId, CarLogsUpdateRequest carLogsUpdateRequest) {

        CarLog carLog = carLogReader.getCarLogById(carLogId);
        carLog.updateCarLog(carLogsUpdateRequest);
        carLog = carLogStore.store(carLog);
        return carLogFactory.toCarLogResponse(carLog);
    }

    //운행일지 상세 정보 삭제
    @Override
    public void deleteCarLogDetails(Long carLogId) {
        carLogStore.delete(carLogId);
    }

    //Todo: 대시보드 코드 추후 별도로 리팩토링 진행
    @Override
    public CarLogResponse getAllCarLogsStatics(Long companyId) {

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
                .toList();

        // 로그 건수
        long count = currentMonthLogs.size();

        // 총 주행 거리 계산
        double totalMileage = currentMonthLogs.stream()
                .mapToDouble(log -> log.getOffMileage() - log.getOnMileage())
                .sum();

        // 최근 6개월간 총 주행거리 계산
        Map<String, Double> monthlyMileageMap = new LinkedHashMap<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate targetDate = now.minusMonths(i);
            int year = targetDate.getYear();
            int month = targetDate.getMonthValue();

            Double mileage = logs.stream()
                    .filter(log -> {
                        LocalDateTime onTime = log.getOnTime();
                        return onTime != null
                                && onTime.getYear() == year
                                && onTime.getMonthValue() == month;
                    })
                    .mapToDouble(log -> log.getOffMileage() - log.getOnMileage())
                    .sum();

            String key = String.format("%d-%02d", year, month);
            monthlyMileageMap.put(key, mileage);
        }

        List<MonthlyMileage> monthlyMileages = monthlyMileageMap.entrySet().stream()
                .map(entry -> new MonthlyMileage(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return CarLogResponse.builder()
                .totalMileage(totalMileage)
                .carLogsCount(String.valueOf(count))
                .monthlyMileages(monthlyMileages)
                .build();
    }


}
