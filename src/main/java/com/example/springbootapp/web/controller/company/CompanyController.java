package com.example.springbootapp.web.controller.company;

import com.example.springbootapp.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/companies")
@Validated
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    ResponseEntity<Collection<CompanyResponseJson>> findAll() {
        log.info("Find all companies");
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findAll());
    }
}
