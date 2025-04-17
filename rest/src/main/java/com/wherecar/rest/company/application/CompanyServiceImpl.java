package com.wherecar.rest.company.application;

import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.application.dto.CompanyResponse;
import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.company.domain.CompanyFactory;
import com.wherecar.rest.company.infrastructure.CompanyReader;
import com.wherecar.rest.company.infrastructure.CompanyStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyFactory companyFactory;

    private final CompanyStore companyStore;
    private final CompanyReader companyReader;


    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyDetails(Long companyId) {
        Company company = companyReader.getCompanyById(companyId);

        return companyFactory.toCompanyResponse(company);
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, CompanyRequest companyRequest) {
        Company company = companyReader.getCompanyById(companyId);
        company.updateCompany(companyRequest);
        company = companyStore.store(company);
        return companyFactory.toCompanyResponse(company);
    }

    @Override
    public void deleteCompany(Long companyId) {
        companyStore.deleteById(companyId);
    }
}
