package com.wherecar.rest.geoinfo.presentation;

import com.wherecar.rest.geoinfo.application.dto.GeoInfoRequest;
import com.wherecar.rest.geoinfo.application.dto.GeoInfoResponse;
import com.wherecar.rest.geoinfo.application.GeoInfoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geoInfos")
@RequiredArgsConstructor
public class GeoInfoController {

    private final GeoInfoService geoInfoService;

    // GeoFence 정보 등록
    @PostMapping("/create")

    public ResponseEntity<Void> geoInfoCreate(HttpServletRequest request, @RequestBody GeoInfoRequest geoInfoRequest) {
        Long companyId = (Long)request.getAttribute("companyId");
        geoInfoService.createGeoInfo(geoInfoRequest, companyId);

        return ResponseEntity.ok().build();

    }

    // Todo: GeoInfo Emulator에 전송
    /*
    * param : GeoInfoReqeust
    * return : geoInfoResponse
    */

    // GeoInfo 조회
    @GetMapping("/{id}")
    public ResponseEntity<GeoInfoResponse> geoInfoGet(@PathVariable Long id) {

        GeoInfoResponse geoInfoResponse = geoInfoService.getGeoInfo(id);

        return ResponseEntity.ok(geoInfoResponse);

    }

    // GeoInfo 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> geoInfoUpdate(@PathVariable Long id, @RequestBody GeoInfoRequest geoInfoRequest) {

        geoInfoService.updateGeoInfo(id, geoInfoRequest);

        return ResponseEntity.ok().build();
    }

    // GeoInfo 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> geoInfoDelete(@PathVariable Long id) {

        geoInfoService.deleteGeoInfo(id);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/companies/my")
    public ResponseEntity<List<GeoInfoResponse>> geoInfoGetByMyCompany(HttpServletRequest request) {
        Long companyId = (Long)request.getAttribute("companyId");
        List<GeoInfoResponse> geoInfoResponses = geoInfoService.getGeoInfosByCompanyId(companyId);
        return ResponseEntity.ok(geoInfoResponses);
    }
}
