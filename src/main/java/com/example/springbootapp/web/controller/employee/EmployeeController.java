package com.example.springbootapp.web.controller.employee;

import com.example.springbootapp.service.EmployeeService;
import com.example.springbootapp.validation.group.OnCreate;
import com.example.springbootapp.validation.group.OnUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/employees")
@Validated // Required for validating @RequestParam/@PathVariable
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    ResponseEntity<Collection<EmployeeResponseJson>> findAll() {
        log.info("Find all employees");
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeResponseJson save(@RequestBody @Validated(OnCreate.class) EmployeeRequestJson json) {
        log.info("Save employee ...");
        return employeeService.save(json);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    EmployeeResponseJson update(@PathVariable String id,
                                @RequestBody @Validated(OnUpdate.class) EmployeeRequestJson json) {
        log.info("Update employee ...");
        return employeeService.update(id, json);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable String id) {
        log.info("Delete employee ...");
        employeeService.deleteAllById(List.of(id));
    }
}
