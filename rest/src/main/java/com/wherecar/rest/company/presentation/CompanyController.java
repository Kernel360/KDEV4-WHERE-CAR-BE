package com.wherecar.rest.company.presentation;

import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.application.dto.CompanyResponse;
import com.wherecar.rest.company.application.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/my")
    public ResponseEntity<CompanyResponse> myCompanyDetailsGet(HttpServletRequest request) {
        Long companyId = (Long)request.getAttribute("companyId");
        CompanyResponse company = companyService.getCompanyDetails(companyId);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/my")
    public ResponseEntity<Void> myCompanyUpdate(HttpServletRequest request, @RequestBody CompanyRequest companyRequest) {
        Long companyId = (Long)request.getAttribute("companyId");
        companyService.updateCompany(companyId, companyRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/my")
    public ResponseEntity<Void> myCompanyDelete(HttpServletRequest request) {
        Long companyId = (Long)request.getAttribute("companyId");
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok().build();
    }
}