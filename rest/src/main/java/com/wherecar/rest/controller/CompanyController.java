package com.wherecar.rest.controller;

import com.wherecar.rest.dto.CompanyRequest;
import com.wherecar.rest.dto.CompanyResponse;
import com.wherecar.rest.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // todo: 해당 주석은 remote CompanyController 파일 복구를 위한 주석으로 삭제 예정입니다.

    // test 업체 등록
//    @PostMapping("/create")
//    public ResponseEntity<String> companyCreate(@RequestBody CompanyRequest companyRequest){
//        System.out.println("companyRequest: "+companyRequest);
//        companyService.createCompany(companyRequest);
//        return ResponseEntity.ok("등록되었습니다.");
//    }

    // 업체 상세정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> companyDetailsGet(@PathVariable Long id) {
        CompanyResponse company = companyService.getCompanyDetails(id);
        return ResponseEntity.ok(company);
    }

    // 업체 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> companyUpdate(@PathVariable Long id, @RequestBody CompanyRequest companyRequest) {
        companyService.updateCompany(id, companyRequest);
        return ResponseEntity.ok("수정되었습니다.");
    }

    // 업체 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> companyDelete(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}