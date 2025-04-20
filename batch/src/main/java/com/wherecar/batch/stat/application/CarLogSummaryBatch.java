package com.wherecar.batch.stat.application;

import com.wherecar.batch.main.domain.CarLog;
import com.wherecar.batch.main.domain.GpsLog;
import com.wherecar.batch.main.infrastructure.CarLogRepository;
import com.wherecar.batch.main.infrastructure.GpsLogRepository;
import com.wherecar.batch.stat.domain.CarLogSummary;
import com.wherecar.batch.stat.infrastructure.CarLogSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CarLogSummaryBatch {


    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final CarLogRepository carLogRepository;
    private final GpsLogRepository gpsLogRepository;
    private final CarLogSummaryRepository carLogSummaryRepository;


    private static final String JOB_NAME = "carLogSummaryJob";
    private static final String STEP_NAME = "carLogSummaryStep";

    @Bean
    public Job carLogSummaryJob(Step carLogSummaryStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(carLogSummaryStep)
                .build();
    }

    @Bean
    public Step carLogSummaryStep(
            RepositoryItemReader<CarLog> carLogReader,
            ItemProcessor<CarLog, CarLogSummary> carLogProcessor,
            ItemWriter<CarLogSummary> carLogWriter
    ) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<CarLog, CarLogSummary>chunk(10, transactionManager)
                .reader(carLogReader)
                .processor(carLogProcessor)
                .writer(carLogWriter)
                .build();
    }

    @Bean
    @StepScope
    public RepositoryItemReader<CarLog> carLogReader(@Value("#{jobParameters['runTime']}") String runTimeStr) {
        if (runTimeStr == null) {
            throw new IllegalArgumentException("jobParameter 'runTime' is missing.");
        }

        LocalDateTime runTime = LocalDateTime.parse(runTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDate targetDate = runTime.toLocalDate().minusDays(1);

        LocalDateTime start = targetDate.atStartOfDay();
        LocalDateTime end = targetDate.atTime(23, 59, 59);

        return new RepositoryItemReaderBuilder<CarLog>()
                .name("carLogReader")
                .repository(carLogRepository)
                .methodName("findByOffTimeBetween")
                .arguments(List.of(start, end))
                .pageSize(10)
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<CarLog, CarLogSummary> carLogProcessor() {
        return carLog -> {
            int pageSize = 1000;
            int page = 0;
            double speedSum = 0.0;
            double maxSpeed = 0.0;
            long totalCount = 0;

            while (true) {
                Pageable pageable = PageRequest.of(page, pageSize, Sort.by("timestamp").ascending());
                Page<GpsLog> gpsPage = gpsLogRepository.findByMdnAndTimestampBetween(
                        carLog.getMdn(),
                        carLog.getOnTime(),
                        carLog.getOffTime(),
                        pageable
                );

                List<GpsLog> gpsLogs = gpsPage.getContent();
                if (gpsLogs.isEmpty()) break;

                for (GpsLog log : gpsLogs) {
                    double speed = log.getSpeed();
                    speedSum += speed;
                    maxSpeed = Math.max(maxSpeed, speed);
                    totalCount++;
                }

                if (!gpsPage.hasNext()) break;
                page++;
            }

            int avgSpeed = totalCount > 0 ? (int) (speedSum / totalCount) : 0;

            return CarLogSummary.builder()
                    .mdn(carLog.getMdn())
                    .onTime(carLog.getOnTime())
                    .offTime(carLog.getOffTime())
                    .onLatitude(carLog.getOnLatitude())
                    .onLongitude(carLog.getOnLongitude())
                    .offLatitude(carLog.getOffLatitude())
                    .offLongitude(carLog.getOffLongitude())
                    .driveType(carLog.getDriveType())
                    .distance(carLog.getOffMileage() - carLog.getOnMileage())
                    .averageSpeed(avgSpeed)
                    .maxSpeed((int) maxSpeed)
                    .build();
        };
    }

    @Bean
    public ItemWriter<CarLogSummary> carLogWriter() {
        return carLogSummaryRepository::saveAll;
    }
}
