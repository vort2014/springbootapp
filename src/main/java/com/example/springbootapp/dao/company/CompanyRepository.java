package com.example.springbootapp.dao.company;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Order(20)
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {

//    @Query("""
//        SELECT company
//        FROM CompanyEntity company JOIN FETCH company.employees
//    """)
//    List<CompanyEntity> findAll();
}
