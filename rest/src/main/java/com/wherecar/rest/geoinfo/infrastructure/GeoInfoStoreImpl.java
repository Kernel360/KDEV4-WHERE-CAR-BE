package com.wherecar.rest.geoinfo.infrastructure;

import com.wherecar.rest.geoinfo.domain.GeoInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeoInfoStoreImpl implements GeoInfoStore {
    private final GeoInfoRepository geoInfoRepository;

    @Override
    public GeoInfo store(GeoInfo geoInfo) {
        return geoInfoRepository.save(geoInfo);
    }

    @Override
    public void delete(Long id) {
        geoInfoRepository.deleteById(id);
    }
}
