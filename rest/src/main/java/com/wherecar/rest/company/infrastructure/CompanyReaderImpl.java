package com.wherecar.rest.company.infrastructure;

import com.wherecar.rest.company.domain.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyReaderImpl implements CompanyReader {
    private final CompanyRepository companyRepository;

    @Override
    public Company getById(Long companyId) {
        return companyRepository.findById(companyId).orElseThrow();
    }
}
