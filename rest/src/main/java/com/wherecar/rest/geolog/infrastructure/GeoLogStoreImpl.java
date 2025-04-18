package com.wherecar.rest.geolog.infrastructure;

import com.wherecar.rest.geolog.domain.GeoLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeoLogStoreImpl implements GeoLogStore {
    private final GeoLogRepository geoLogRepository;

    @Override
    public GeoLog store(GeoLog geoLog) {
        return geoLogRepository.save(geoLog);
    }

    @Override
    public void deleteById(Long geoLogId) {
        geoLogRepository.deleteById(geoLogId);
    }
}
