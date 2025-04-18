package com.wherecar.rest.geolog.infrastructure;

import com.wherecar.rest.geolog.domain.GeoLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeoLogReaderImpl implements GeoLogReader {
    private final GeoLogRepository geoLogRepository;

    @Override
    public List<GeoLog> getGeoLogsByMdn(String mdn) {
        List<GeoLog> geoLogs = geoLogRepository.findByMdn(mdn);

        if (geoLogs.isEmpty()) {
            throw new RuntimeException("GeoLog not found");
        }

        return geoLogs;
    }

    @Override
    public GeoLog getGeoLogById(Long geoLogId) {
        return geoLogRepository.findById(geoLogId).orElseThrow(() -> new RuntimeException("GeoLog not found"));
    }

}
