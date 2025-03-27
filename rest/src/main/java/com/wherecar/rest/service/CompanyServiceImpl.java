package com.wherecar.rest.service;

import com.wherecar.rest.domain.Company;
import com.wherecar.rest.dto.CompanyRequest;
import com.wherecar.rest.dto.CompanyResponse;
import com.wherecar.rest.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse getCompanyDetails(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("회사 세부 정보를 찾을수 없습니다."));

        return CompanyResponse.builder()
                .id(company.getId())
                .address(company.getAddress())
                .email(company.getEmail())
                .name(company.getName())
                .phone(company.getPhone())
                .website(company.getWebsite())
                .build();
    }

    @Override
    public void updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("회사 정보를 찾을수 없습니다."));

        log.info("requestinfo: {}", companyRequest);

        Company updateCompany = Company.builder()
                .id(company.getId())
                .name(companyRequest.getName())
                .address(companyRequest.getAddress())
                .phone(companyRequest.getPhone())
                .email(companyRequest.getEmail())
                .website(companyRequest.getWebsite())
                .build();

        companyRepository.save(updateCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        if(!companyRepository.existsById(id)){
            throw new RuntimeException("회사정보를 찾을 수 없습니다.");
        }
        companyRepository.deleteById(id);
    }

}
