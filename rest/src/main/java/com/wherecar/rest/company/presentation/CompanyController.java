package com.wherecar.rest.company.presentation;

import com.wherecar.rest.common.response.BaseResponse;
import com.wherecar.rest.company.application.CompanyService;
import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.application.dto.CompanyResponse;
import com.wherecar.rest.security.aspect.RequiredPermission;
import com.wherecar.rest.user.domain.constant.PermissionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Todo: 권한 체크 추후 추가 예정

@Slf4j
@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @RequiredPermission(PermissionType.PERM_COMPANY_VIEW)
    @GetMapping("/my")
    public ResponseEntity<BaseResponse<CompanyResponse>> myCompanyDetailsGet(HttpServletRequest request) {
        Long companyId = (Long)request.getAttribute("companyId");
        CompanyResponse companyResponse = companyService.getCompanyDetails(companyId);
        return BaseResponse.ok(companyResponse);
    }


    @RequiredPermission(PermissionType.PERM_COMPANY_EDIT)
    @PutMapping("/my")
    public ResponseEntity<BaseResponse<CompanyResponse>> myCompanyUpdate(HttpServletRequest request, @RequestBody @Valid CompanyRequest companyRequest) {
        Long companyId = (Long)request.getAttribute("companyId");
        CompanyResponse companyResponse = companyService.updateCompany(companyId, companyRequest);
        return BaseResponse.created(companyResponse);
    }

    @DeleteMapping("/my")
    public ResponseEntity<BaseResponse<Void>> myCompanyDelete(HttpServletRequest request) {
        Long companyId = (Long)request.getAttribute("companyId");
        companyService.deleteCompany(companyId);
        return BaseResponse.ok();
    }
}