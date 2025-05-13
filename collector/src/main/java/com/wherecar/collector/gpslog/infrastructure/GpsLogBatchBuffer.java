package com.wherecar.collector.gpslog.infrastructure;

import com.wherecar.collector.gpslog.domain.GpsLog;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GpsLogBatchBuffer {

    private final GpsLogJdbcRepository gpsLogJdbcRepository;

    private final List<GpsLog> buffer = new ArrayList<>();
    private static final int BATCH_SIZE = 10000;
    private static final long FLUSH_INTERVAL_MILLIS = 60_000;

    private long lastFlushTime = System.currentTimeMillis();

    public synchronized void add(GpsLog log) {
        buffer.add(log);
        boolean batchReady = buffer.size() >= BATCH_SIZE;
        boolean timeExceeded = System.currentTimeMillis() - lastFlushTime >= FLUSH_INTERVAL_MILLIS;

        if (batchReady || timeExceeded) {
            flush();
        }
    }

    @Scheduled(fixedRate = 60_000)
    public synchronized void flushByScheduler() {
        if (!buffer.isEmpty()) {
            flush();
        }
    }

    private void flush() {
        try {
            List<GpsLog> toFlush = new ArrayList<>(buffer);
            buffer.clear();
            lastFlushTime = System.currentTimeMillis();

            gpsLogJdbcRepository.batchInsert(toFlush);
            System.out.println("[GpsLogBatchBuffer] Flushed " + toFlush.size() + " logs.");
        } catch (Exception e) {
            System.err.println("[GpsLogBatchBuffer] flush 실패: " + e.getMessage());
        }
    }
}
