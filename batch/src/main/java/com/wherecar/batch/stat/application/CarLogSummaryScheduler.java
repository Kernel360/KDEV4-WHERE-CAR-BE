package com.wherecar.batch.stat.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarLogSummaryScheduler {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Scheduled(cron = "0 0 1 * * *")  // 매일 새벽 1시 실행
    public void runJob() {
        try {
            Map<String, JobParameter<?>> params = new HashMap<>();

            params.put("runTime", new JobParameter<>(LocalDateTime.now().toString(), String.class)); // 기준 시간만 전달

            JobParameters jobParameters = new JobParameters(params);

            JobExecution jobExecution = jobLauncher.run(jobRegistry.getJob("carLogSummaryJob"), jobParameters);
            log.info("CarLogSummaryJob 실행 완료. 상태: {}", jobExecution.getStatus());

        } catch (Exception e) {
            log.error("CarLogSummaryJob 실행 실패", e);
        }
    }

}
