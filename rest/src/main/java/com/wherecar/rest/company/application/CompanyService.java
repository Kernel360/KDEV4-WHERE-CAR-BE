package com.wherecar.rest.company.application;

import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.application.dto.CompanyResponse;

public interface CompanyService {
    CompanyResponse getCompanyDetails(Long companyId);
    CompanyResponse updateCompany(Long companyId, CompanyRequest companyRequest);
    void deleteCompany(Long companyId);
}
