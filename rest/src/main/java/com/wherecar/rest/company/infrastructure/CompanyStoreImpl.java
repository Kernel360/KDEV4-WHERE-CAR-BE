package com.wherecar.rest.company.infrastructure;

import com.wherecar.rest.company.domain.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyStoreImpl implements CompanyStore {
    private final CompanyRepository companyRepository;

    @Override
    public Company Store(Company company) {
        return companyRepository.save(company);
    }
}
