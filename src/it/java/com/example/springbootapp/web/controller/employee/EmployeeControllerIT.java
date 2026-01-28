package com.example.springbootapp.web.controller.employee;

import com.example.springbootapp.ApplicationIT;
import com.example.springbootapp.dao.company.CompanyEntity;
import com.example.springbootapp.dao.company.CompanyRepository;
import com.example.springbootapp.dao.employee.EmployeeEntity;
import com.example.springbootapp.dao.employee.EmployeeRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyOrNullString;

public class EmployeeControllerIT extends ApplicationIT {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("GIVEN startDate is after endDate WHEN request is sent THEN 400 is returned")
    void startBeforeEndDateValidation() {
        // Given
        var firstName = "John";
        var lastName = "Dawn";
        var email = "john.dawn@example.com";
        var salary = BigDecimal.valueOf(10_000.123D);
        var startDate = ZonedDateTime.now().minusYears(1).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var endDate = ZonedDateTime.now().minusYears(2).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var employee = EmployeeRequestJson.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .salary(salary)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(employee).
        when()
                .post("/employees").
        then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("[0]", equalTo("startDate should be before endDate"));
    }

    @Test
    @DisplayName("GIVEN startDate is in the future WHEN request is sent THEN 400 is returned")
    void validStartDate() {
        // Given
        var firstName = "John";
        var lastName = "Dawn";
        var email = "john.dawn@example.com";
        var salary = BigDecimal.valueOf(10_000.123D);
        var startDate = ZonedDateTime.now().plusYears(2).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var endDate = ZonedDateTime.now().minusYears(1).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var employee = EmployeeRequestJson.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .salary(salary)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(employee).
        when()
                .post("/employees").
        then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("", hasItems("startDate must be in the past", "startDate should be before endDate"));
    }

    @Test
    @DisplayName("GIVEN user with Id WHEN execute post '/employees' rest call THEN 400 is returned")
    void saveWithId() {
        given()
                .contentType(ContentType.JSON)
                .body(EmployeeRequestJson.builder()
                        .id(UUID.randomUUID().toString())
                        .firstName("John")
                        .lastName("Dawn")
                        .email("john.dawn@example.com")
                        .salary(BigDecimal.valueOf(10_000.123D))
                        .startDate(ZonedDateTime.now().minusYears(2).toInstant().truncatedTo(ChronoUnit.MILLIS))
                        .endDate(ZonedDateTime.now().minusYears(1).toInstant().truncatedTo(ChronoUnit.MILLIS))
                        .build()).
        when()
                .post("/employees").
        then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("[0]", equalTo("id must not be present"));
    }

    @Test
    @DisplayName("GIVEN user without Id WHEN execute put '/employees' rest call THEN 400 is returned")
    void updateWithoutId() {
        var id = UUID.randomUUID().toString();
        given()
            .contentType(ContentType.JSON)
            .body(EmployeeRequestJson.builder()
                    .firstName("John")
                    .lastName("Dawn")
                    .email("john.dawn@example.com")
                    .salary(BigDecimal.valueOf(10_000.123D))
                    .startDate(ZonedDateTime.now().minusYears(2).toInstant().truncatedTo(ChronoUnit.MILLIS))
                    .endDate(ZonedDateTime.now().minusYears(1).toInstant().truncatedTo(ChronoUnit.MILLIS))
                    .build()).
        when()
            .put("/employees/{id}", id).
        then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("[0]", equalTo("id should not be blank"));
    }

    @Test
    @DisplayName("GIVEN user WHEN execute post '/employees' rest call THEN user is inserted into the database")
    void save() {

        // Given
        var firstName = "John";
        var lastName = "Dawn";
        var email = "john.dawn@example.com";
        var salary = BigDecimal.valueOf(10_000.123D);
        var startDate = ZonedDateTime.now().minusYears(2).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var endDate = ZonedDateTime.now().minusYears(1).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var employee = EmployeeRequestJson.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .salary(salary)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(employee).
        when()
                .post("/employees").
        then()
                .statusCode(HttpStatus.CREATED.value())
                .body(
                        "firstName", equalTo(firstName),
                        "lastName", equalTo(lastName),
                        "email", equalTo(email),
                        "startDate", equalTo(startDate.toString()),
                        "endDate", equalTo(endDate.toString()),
                        "companyName", isEmptyOrNullString()
                );
    }

    @Test
    @DisplayName("GIVEN a user in the database WHEN execute '/employees' rest call THEN user is returned")
    void findAll() {

        // Given
        var employeeId = UUID.randomUUID().toString();
        var firstName = "John";
        var lastName = "Dawn";
        var email = "john.dawn@example.com";
        var salary = BigDecimal.valueOf(10_000.123D);
        var startDate = ZonedDateTime.now().minusYears(2).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var endDate = ZonedDateTime.now().minusYears(1).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var companyName = "Test Company";
        var company = CompanyEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(companyName)
                .build();
        company = companyRepository.save(company);
        var employee = EmployeeEntity.builder()
                .id(employeeId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .salary(salary)
                .startDate(startDate)
                .endDate(endDate)
                .company(company)
                .build();
        employeeRepository.save(employee);

        when()
                .get("/employees").
        then()
                .statusCode(HttpStatus.OK.value())
                .body(
                        "$", hasSize(1),
                        "[0].id", equalTo(employeeId),
                        "[0].firstName", equalTo(firstName),
                        "[0].lastName", equalTo(lastName),
                        "[0].email", equalTo(email),
                        "[0].startDate", equalTo(startDate.toString()),
                        "[0].endDate", equalTo(endDate.toString()),
                        "[0].companyName", equalTo(companyName)
                );
    }

    @Test
    @DisplayName("GIVEN a user in the database WHEN execute delete '/employees' rest call THEN user is deleted")
    void delete() {

        // Given
        var employeeId = UUID.randomUUID().toString();
        var firstName = "John";
        var lastName = "Dawn";
        var email = "john.dawn@example.com";
        var salary = BigDecimal.valueOf(10_000.123D);
        var startDate = ZonedDateTime.now().minusYears(2).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var endDate = ZonedDateTime.now().minusYears(1).toInstant().truncatedTo(ChronoUnit.MILLIS);
        var companyName = "Test Company";
        var company = CompanyEntity.builder()
                .id(UUID.randomUUID().toString())
                .name(companyName)
                .build();
        company = companyRepository.save(company);
        var employee = EmployeeEntity.builder()
                .id(employeeId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .salary(salary)
                .startDate(startDate)
                .endDate(endDate)
                .company(company)
                .build();
        employeeRepository.save(employee);

        when()
                .delete("/employees/{id}", employeeId).
        then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        assertThat(employeeRepository.findById(employeeId)).isEmpty();
    }
}
