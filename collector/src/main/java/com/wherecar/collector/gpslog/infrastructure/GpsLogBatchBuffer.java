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

    private final List<GpsLog> buffer = Collections.synchronizedList(new ArrayList<>());

    // ✅ 배치 사이즈 & flush 주기 최적값
    private static final int BATCH_SIZE = 1000;
    private static final long FLUSH_INTERVAL_MILLIS = 5000L;

    private long lastFlushTime = System.currentTimeMillis();

    public void add(GpsLog log) {
        buffer.add(log);
        maybeFlush();
    }

    private synchronized void maybeFlush() {
        boolean batchReady = buffer.size() >= BATCH_SIZE;
        boolean timeExceeded = System.currentTimeMillis() - lastFlushTime >= FLUSH_INTERVAL_MILLIS;

        if (batchReady || timeExceeded) {
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
            lastFlushTime = System.currentTimeMillis();
            log.info("[GpsLogBatchBuffer] ✅ Flushed {} logs", toFlush.size());
        } catch (Exception e) {
            log.error("[GpsLogBatchBuffer] ❌ Flush failed, restoring {} logs to buffer", toFlush.size(), e);
            synchronized (this) {
                buffer.addAll(0, toFlush); // 앞에 복구
            }
        }
    }
}