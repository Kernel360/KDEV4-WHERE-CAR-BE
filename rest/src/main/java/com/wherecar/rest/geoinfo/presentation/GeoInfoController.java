package com.wherecar.rest.geoinfo.presentation;

import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.geoinfo.application.GeoInfoService;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoRequest;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Todo: 권한 체크 추후 추가 예정

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/geoInfos")
@RequiredArgsConstructor
public class GeoInfoController {

    private final GeoInfoService geoInfoService;

    // GeoFence 정보 등록
    @PostMapping
    public ResponseEntity<BaseResponse<GeoInfoResponse>> geoInfoCreate(HttpServletRequest request, @RequestBody GeoInfoRequest geoInfoRequest) {
        Long companyId = (Long)request.getAttribute("companyId");
        GeoInfoResponse geoInfoResponse = geoInfoService.createGeoInfo(companyId, geoInfoRequest);

        return BaseResponse.created(geoInfoResponse);

    }

    // Todo: GeoInfo Emulator에 전송
    /*
    * param : GeoInfoReqeust
    * return : geoInfoResponse
    */

    // GeoInfo 조회
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<GeoInfoResponse>> geoInfoGet(@PathVariable Long id) {

        GeoInfoResponse geoInfoResponse = geoInfoService.getGeoInfo(id);

        return BaseResponse.ok(geoInfoResponse);

    }

    // GeoInfo 수정
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<GeoInfoResponse>> geoInfoUpdate(@PathVariable Long id, @RequestBody GeoInfoRequest geoInfoRequest) {

        GeoInfoResponse geoInfoResponse = geoInfoService.updateGeoInfo(id, geoInfoRequest);

        return BaseResponse.created(geoInfoResponse);
    }

    // GeoInfo 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> geoInfoDelete(@PathVariable Long id) {

        geoInfoService.deleteGeoInfo(id);

        return BaseResponse.ok();

    }

    @GetMapping("/companies/my")
    public ResponseEntity<BaseResponse<List<GeoInfoResponse>>> geoInfoGetByMyCompany(HttpServletRequest request) {
        Long companyId = (Long)request.getAttribute("companyId");
        List<GeoInfoResponse> geoInfoResponses = geoInfoService.getGeoInfosByCompanyId(companyId);
        return BaseResponse.ok(geoInfoResponses);
    }
}
