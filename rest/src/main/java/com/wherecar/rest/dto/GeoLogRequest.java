package com.wherecar.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoLogRequest {
    private String mdn;
    private String tid;
    private String mid;
    private String pv;
    private String did;
    private String oTime;
    private String geoGrpId;
    private String geoPId;
    private String evtVal;
    private String gcd;
    private String lat;
    private String lon;
    private String ang;
    private String spd;
    private String sum;
}
