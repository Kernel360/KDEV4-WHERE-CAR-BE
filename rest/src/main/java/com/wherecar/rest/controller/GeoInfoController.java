package com.wherecar.rest.controller;

import com.wherecar.rest.constants.PaginationConstants;
import com.wherecar.rest.dto.*;
import com.wherecar.rest.service.GeoInfoService;
import com.wherecar.rest.user.auth.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geoInfo")
@RequiredArgsConstructor
public class GeoInfoController {

    private final GeoInfoService geoInfoService;

    // GeoFence 정보 등록
    @PostMapping("/create")
    public ResponseEntity<String> geoInfoCreate(@RequestBody GeoFenceRequest geoFenceRequest) {

        Long companyId = AuthUtil.getCompanyId();

        geoInfoService.createGeoInfo(geoFenceRequest, companyId);

        return ResponseEntity.ok("등록 완료되었습니다.");

    }

    // company별 GeoFence 조회
    @GetMapping("/")
    public ResponseEntity<List<GeoFenceResponse>> geoFenceList(
            @RequestParam(value = "page", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = "" + PaginationConstants.DEFAULT_PAGE) int size
    ) {
        Long companyId = AuthUtil.getCompanyId();

        List<GeoFenceResponse> geoFences = geoInfoService.getGeoFences(companyId, page, size);
        return ResponseEntity.ok(geoFences);
    }

    // GeoInfo 조회
    @GetMapping("/{id}")
    public ResponseEntity<GeoFenceResponse> geoInfoRead(@PathVariable Long id) {

        GeoFenceResponse geoFenceResponse = geoInfoService.getGeoInfo(id);

        return ResponseEntity.ok(geoFenceResponse);

    }

    // GeoInfo 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> geoInfoUpdate(@PathVariable Long id, @RequestBody GeoFenceRequest geoFenceRequest) {

        geoInfoService.updateGeoInfo(id, geoFenceRequest);

        return ResponseEntity.ok("수정 완료되었습니다.");
    }

    // GeoInfo 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> geoInfoDelete(@PathVariable Long id) {

        geoInfoService.deleteGeoInfo(id);

        return ResponseEntity.ok("삭제 완료되었습니다.");

    }

    // Todo: GeoInfo Emulator에 전송
    /*
     * param : GeoInfoReqeust
     * return : geoInfoResponse
     */

}
