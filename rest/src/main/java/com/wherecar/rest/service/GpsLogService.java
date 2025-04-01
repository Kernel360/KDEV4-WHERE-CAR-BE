package com.wherecar.rest.service;

import com.wherecar.rest.dto.GpsLogResponse;

public interface GpsLogService {
    GpsLogResponse getLatestLocation(String mdn);
}
