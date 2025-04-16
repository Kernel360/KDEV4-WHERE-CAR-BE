package com.wherecar.rest.company.application;

import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.application.dto.CompanyResponse;

public interface CompanyService {
    Company createCompany(CompanyRequest companyRequest);
    CompanyResponse getCompanyDetails(Long id);
    void updateCompany(Long id, CompanyRequest companyRequest);
    void deleteCompany(Long id);
}
