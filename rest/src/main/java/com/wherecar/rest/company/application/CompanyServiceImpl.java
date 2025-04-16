package com.wherecar.rest.company.application;

import com.wherecar.rest.company.domain.Company;
import com.wherecar.rest.company.application.dto.CompanyRequest;
import com.wherecar.rest.company.application.dto.CompanyResponse;
import com.wherecar.rest.company.infrastructure.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company createCompany(CompanyRequest companyRequest) {

        // 요청 데이터를 엔터티로 변환
        Company company = Company.builder()
                .address(companyRequest.getAddress())
                .email(companyRequest.getEmail())
                .name(companyRequest.getName())
                .phone(companyRequest.getPhone())
                .website(companyRequest.getWebsite())
                .description(companyRequest.getDescription())
                .build();

        // 요청 데이터 저장
        companyRepository.save(company);

        return company;
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponse getCompanyDetails(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("회사 세부 정보를 찾을수 없습니다."));

        return CompanyResponse.builder()
                .id(company.getId())
                .address(company.getAddress())
                .email(company.getEmail())
                .name(company.getName())
                .phone(company.getPhone())
                .website(company.getWebsite())
                .description(company.getDescription())
                .build();
    }

    @Override
    public void updateCompany(Long id, CompanyRequest companyRequest) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("회사 정보를 찾을수 없습니다."));

        Company updateCompany = Company.builder()
                .id(company.getId())
                .name(companyRequest.getName())
                .address(companyRequest.getAddress())
                .phone(companyRequest.getPhone())
                .email(companyRequest.getEmail())
                .website(companyRequest.getWebsite())
                .description(companyRequest.getDescription())
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
