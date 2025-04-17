package com.wherecar.rest.company.infrastructure;

import com.wherecar.rest.company.domain.Company;

public interface CompanyStore {
    Company store(Company company);
    void deleteById(Long companyId);
}
