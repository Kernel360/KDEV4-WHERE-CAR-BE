package com.wherecar.rest.service;

import com.wherecar.rest.domain.Company;
import com.wherecar.rest.dto.CompanyRequest;
import com.wherecar.rest.dto.CompanyResponse;

public interface CompanyService {
    Company createCompany(CompanyRequest companyRequest);
    CompanyResponse getCompanyDetails(Long id);
    void updateCompany(Long id, CompanyRequest companyRequest);
    void deleteCompany(Long id);
}
