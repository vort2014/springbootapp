package com.example.springbootapp;

import com.example.springbootapp.dao.company.CompanyRepository;
import com.example.springbootapp.dao.employee.EmployeeRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public abstract class ApplicationIT {

    @LocalServerPort
    private Integer port;

    // Repositories
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); // enableLoggingOfRequestAndResponse for all tests
    }

    @AfterEach
    void afterEach() {
        clearDatabase();
    }

    /**
     * Clear all data in the database
     */
    private void clearDatabase() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
    }
}
