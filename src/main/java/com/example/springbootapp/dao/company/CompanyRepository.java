package com.example.springbootapp.dao.company;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@Order(20)
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
}
