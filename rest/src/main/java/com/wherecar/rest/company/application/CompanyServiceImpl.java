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
        log.info("[COMPANY][CompanyServiceImpl][getCompanyDetails] 시작 | companyId={}", companyId);
        Company company = companyReader.getCompanyById(companyId);

        CompanyResponse companyResponse = companyFactory.toCompanyResponse(company);
        log.info("[COMPANY][CompanyServiceImpl][getCompanyDetails] 끝 | companyResponse={}", companyResponse);
        return companyResponse;
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, CompanyRequest companyRequest) {
        log.info("[COMPANY][CompanyServiceImpl][updateCompany] 시작 | companyId={}, companyRequest={}", companyId, companyRequest);
        Company company = companyReader.getCompanyById(companyId);
        company.updateCompany(companyRequest);
        company = companyStore.store(company);
        CompanyResponse companyResponse = companyFactory.toCompanyResponse(company);
        log.info("[COMPANY][CompanyServiceImpl][updateCompany] 끝 | companyResponse={}", companyResponse);
        return companyResponse;
    }

    @Override
    public void deleteCompany(Long companyId) {
        log.info("[COMPANY][CompanyServiceImpl][deleteCompany] 시작 | companyId={}", companyId);
        companyStore.delete(companyId);
        log.info("[COMPANY][CompanyServiceImpl][deleteCompany] 끝");
    }
}
