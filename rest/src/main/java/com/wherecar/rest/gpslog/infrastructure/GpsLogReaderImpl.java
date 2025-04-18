package com.wherecar.rest.gpslog.infrastructure;

import com.wherecar.rest.gpslog.application.dto.GpsPoint;
import com.wherecar.rest.gpslog.domain.GpsLog;
import com.wherecar.rest.gpslog.domain.GpsLogFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class GpsLogReaderImpl implements GpsLogReader {

    private final GpsLogRepository gpsLogRepository;
    private final GpsLogFactory gpsLogFactory;

    @Override
    public GpsLog findTopByMdnOrderByTimestampDesc(String mdn) {
        return gpsLogRepository.findTopByMdnOrderByTimestampDesc(mdn)
                .orElseThrow(() -> new RuntimeException("해당 차량의 GPS 로그가 없습니다."));
    }

    @Override
    public List<GpsPoint> findByMdnAndTimestampBetweenOrderByTimestamp(String mdn, LocalDateTime startTime, LocalDateTime endTime) {

        List<GpsLog> gpsLogs = gpsLogRepository.findByMdnAndTimestampBetweenOrderByTimestamp(mdn, startTime, endTime);

        return gpsLogFactory.route(gpsLogs);
    }
}
