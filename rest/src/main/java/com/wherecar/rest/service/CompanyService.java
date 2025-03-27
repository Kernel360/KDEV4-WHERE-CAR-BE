package com.wherecar.rest.service;

import com.wherecar.rest.dto.CompanyRequest;
import com.wherecar.rest.dto.CompanyResponse;

// 동사 + 명사
public interface CompanyService {
    CompanyResponse getCompanyDetails(Long id);
    void updateCompany(Long id, CompanyRequest companyRequest);
    void deleteCompany(Long id);
}
