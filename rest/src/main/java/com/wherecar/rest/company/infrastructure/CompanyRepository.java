package com.wherecar.rest.company.infrastructure;

import com.wherecar.rest.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
