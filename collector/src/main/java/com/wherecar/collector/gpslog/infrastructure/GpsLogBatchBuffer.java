package com.wherecar.collector.gpslog.infrastructure;

import com.wherecar.collector.gpslog.domain.GpsLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@Slf4j
@Component
@RequiredArgsConstructor
public class GpsLogBatchBuffer {

    private final GpsLogJdbcRepository gpsLogJdbcRepository;

    // ✅ 동기화 리스트
    private final List<GpsLog> buffer = Collections.synchronizedList(new ArrayList<>());

    // ✅ 설정
    private static final int BATCH_SIZE = 75000;
    private static final long FLUSH_INTERVAL_MILLIS = 60000L;

    public void add(GpsLog log) {
        buffer.add(log);
        if (buffer.size() >= BATCH_SIZE) {
            flush();
        }
    }

    @Scheduled(fixedRate = FLUSH_INTERVAL_MILLIS)
    public void flushByScheduler() {
        synchronized (this) {
            if (!buffer.isEmpty()) {
                flush();
            }
        }
    }

    private void flush() {
        List<GpsLog> toFlush;
        synchronized (this) {
            if (buffer.isEmpty()) return;
            toFlush = new ArrayList<>(buffer);
            buffer.clear();
        }

        try {
            gpsLogJdbcRepository.batchInsert(toFlush);
            log.info("[GpsLogBatchBuffer] ✅ Flushed {} logs", toFlush.size());
        } catch (Exception e) {
            log.error("[GpsLogBatchBuffer] ❌ Flush failed. Restoring {} logs", toFlush.size(), e);
            synchronized (this) {
                buffer.addAll(0, toFlush);
            }
        }
    }
}