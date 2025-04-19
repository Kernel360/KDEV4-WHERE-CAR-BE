package com.wherecar.rest.geoinfo.infrastructure;

import com.wherecar.rest.geoinfo.domain.GeoInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GeoInfoReaderImpl implements GeoInfoReader {
    private final GeoInfoRepository geoInfoRepository;

    @Override
    public GeoInfo getGeoInfoById(Long geoInfoId) {
        return geoInfoRepository.findById(geoInfoId).orElseThrow(() -> new RuntimeException("GeoInfo 없음"));
    }

    @Override
    public List<GeoInfo> getGeoInfosByCompanyId(Long companyId) {
        return geoInfoRepository.findByCompanyId(companyId);
    }
}
