package com.wherecar.rest.controller;

import com.wherecar.rest.domain.GeoInfo;
import com.wherecar.rest.dto.GeoInfoRegistRequest;
import com.wherecar.rest.dto.GeoInfoRequest;
import com.wherecar.rest.dto.GeoInfoResponse;
import com.wherecar.rest.dto.GeoList;
import com.wherecar.rest.service.GeoInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geoInfo")
@RequiredArgsConstructor
public class GeoInfoController {

    private GeoInfoService geoInfoService;

    // GeoFence 정보 등록
    @PostMapping("/create")
    public ResponseEntity<String> geoInfoCreate(@RequestBody GeoInfoRegistRequest geoInfoRegistRequest) {

        geoInfoService.createGeoInfo(geoInfoRegistRequest);

        return ResponseEntity.ok("등록 완료되었습니다.");

    }

    // Todo: GeoInfo Emulator에 전송
    /*
    * param : GeoInfoReqeust
    * return : geoInfoResponse
    */

    // GeoInfo 조회
    @GetMapping("/{id}")
    public ResponseEntity<GeoInfo> geoInfoRead(@PathVariable Long id) {

        GeoInfo geoInfoList = geoInfoService.getGeoInfo(id);

        return ResponseEntity.ok(geoInfoList);

    }

    // GeoInfo 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> geoInfoUpdate(@PathVariable Long id, @RequestBody GeoInfoRegistRequest geoInfoRegistRequest) {

        geoInfoService.updateGeoInfo(id, geoInfoRegistRequest);

        return ResponseEntity.ok("수정 완료되었습니다.");
    }

    // GeoInfo 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> geoInfoDelete(@PathVariable Long id) {

        geoInfoService.deleteGeoInfo(id);

        return ResponseEntity.ok("삭제 완료되었습니다.");

    }
}
