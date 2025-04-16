package com.wherecar.rest.company.presentation;

import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.application.dto.CompanyResponse;
import com.wherecar.rest.company.application.CompanyService;
import com.wherecar.rest.common.auth.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/my")
    public ResponseEntity<CompanyResponse> myCompanyDetailsGet() {
        Long companyId = AuthUtil.getCompanyId();
        CompanyResponse company = companyService.getCompanyDetails(companyId);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/my")
    public ResponseEntity<Void> myCompanyUpdate(@RequestBody CompanyRequest companyRequest) {
        Long companyId = AuthUtil.getCompanyId();
        companyService.updateCompany(companyId, companyRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/my")
    public ResponseEntity<Void> myCompanyDelete() {
        Long companyId = AuthUtil.getCompanyId();
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok().build();
    }
}