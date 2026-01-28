package com.example.springbootapp.web.controller.company;

import com.example.springbootapp.ApplicationIT;
import com.example.springbootapp.dao.company.CompanyEntity;
import com.example.springbootapp.dao.company.CompanyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class CompanyControllerIT extends ApplicationIT {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("GIVEN a company in the database WHEN execute '/companies' rest call THEN company is returned")
    void findAll() {

        // Given
        var companyId = UUID.randomUUID().toString();
        companyRepository.save(CompanyEntity.builder()
                .id(companyId)
                .name("companyName")
                .build());

        when()
                .get("/companies").
        then()
                .statusCode(HttpStatus.OK.value())
                .body(
                        "$", hasSize(1),
                        "[0].id", equalTo(companyId),
                        "[0].name", equalTo("companyName")
                );
    }
}
