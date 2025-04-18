package com.wherecar.rest.geoinfo.infrastructure;

import com.wherecar.rest.geoinfo.domain.GeoInfo;

import java.util.List;

public interface GeoInfoReader {
    GeoInfo getGeoInfoById(Long geoInfoId);
    List<GeoInfo> getGeoInfosByCompanyId(Long companyId);
}
