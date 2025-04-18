package com.wherecar.rest.company.infrastructure;

import com.wherecar.rest.company.domain.Company;

public interface CompanyReader {
    Company getCompanyById(Long companyId);
}
