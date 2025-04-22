package com.wherecar.batch.stat.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CarLogSummaryJobController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("MM-dd");
    private static final DateTimeFormatter RUNTIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @GetMapping("/api/batch/car-log-summary")
    public String runCarLogSummaryJob(@RequestParam("date") String date) {
        try {
            // 1. MM-dd 파싱은 MonthDay로
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM-dd");
            MonthDay monthDay = MonthDay.parse(date, inputFormatter);

            // 2. 현재 연도 붙여서 LocalDate 생성
            int year = LocalDate.now().getYear();
            LocalDate fullDate = monthDay.atYear(year);
            LocalDateTime runTime = fullDate.atTime(1, 0); // 01:00 고정

            // 3. ISO_LOCAL_DATE_TIME 포맷으로 문자열 변환
            String runTimeStr = runTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("runTime", runTimeStr)
                    .toJobParameters();

            jobLauncher.run(jobRegistry.getJob("carLogSummaryJob"), jobParameters);

            log.info("✅ Batch Job 실행 요청 성공 (runTime: {})", runTimeStr);
            return "CarLogSummaryJob 실행 요청 완료 (runTime: " + runTimeStr + ")";
        } catch (Exception e) {
            log.error("❌ Batch Job 실행 중 오류 발생", e);
            return "Batch Job 실행 실패: " + e.getMessage();
        }
    }
}
