package com.example.springbootapp.service;

import com.example.springbootapp.dao.employee.EmployeeEntity;
import com.example.springbootapp.dao.employee.EmployeeRepository;
import com.example.springbootapp.web.controller.employee.EmployeeRequestJson;
import com.example.springbootapp.web.controller.employee.EmployeeResponseJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Insert new employee in the database
     *
     * @param json employee without id
     * @return employee with assigned id
     */
    @Transactional
    public EmployeeResponseJson save(EmployeeRequestJson json) {
        log.info("Saving employee ...");
        var entity = EmployeeEntity.from(json);
        employeeRepository.save(entity);
        var res = EmployeeResponseJson.from(entity);
        return res;
    }

    /**
     * Update single employee by id
     *
     * @param id employee id
     * @param json updated user
     * @return updated user
     */
    @Transactional
    public EmployeeResponseJson update(String id, EmployeeRequestJson json) {
        log.info("Update employee with id={}", id);

        // Check that id in the url path is equal to the id in the json body
        if (!json.getId().equals(id)) {
            String errorMessage = "Employee id in the URL '" + id + "' should be equal to employee id in the body '" + json.getId() + "'";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
        }
        // Check if user exists in the database
        if (!employeeRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Employee with id %s not found", id));

        // Update employee
        var entity = employeeRepository.save(EmployeeEntity.from(json));
        var res = EmployeeResponseJson.from(entity);
        return res;
    }

    /**
     * Find all employee in the database
     * @return Collection of employee
     */
    @Transactional(readOnly = true)
    public Collection<EmployeeResponseJson> findAll() {
        log.info("Finding all employees ...");
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeResponseJson::from)
                .toList();
    }

    /**
     * Delete several employee by ids
     * @param ids
     */
    @Transactional
    public void deleteAllById(Collection<String> ids) {
        log.info("Deleting employees by ids {}", ids);
        employeeRepository.deleteAllById(ids);
    }
}
