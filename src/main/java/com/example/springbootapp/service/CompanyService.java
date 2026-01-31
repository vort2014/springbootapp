package com.example.springbootapp.service;

import com.example.springbootapp.dao.company.CompanyRepository;
import com.example.springbootapp.web.controller.company.CompanyResponseJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final CompanyRepository companyRepository;

    /**
     * Find all companies in the database
     * @return Collection of companies
     */
    @Transactional(readOnly = true)
    public Collection<CompanyResponseJson> findAll() {
        log.info("Finding all employees ...");
        return companyRepository.findAll()
                .stream()
                .map(CompanyResponseJson::from)
                .toList();
    }
}
