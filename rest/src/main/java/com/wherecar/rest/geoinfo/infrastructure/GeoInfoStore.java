package com.wherecar.rest.geoinfo.infrastructure;

import com.wherecar.rest.geoinfo.domain.GeoInfo;

public interface GeoInfoStore {
    GeoInfo store(GeoInfo geoInfo);
    void delete(Long id);
}
