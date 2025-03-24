package com.practice.hrbank.repository;

import com.practice.hrbank.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    boolean existsByName(String name);
    Optional<Department> findByName(String name);
    Page<Department> findByNameContainingOrDescriptionContaining(String name, String description, Pageable pageable);



}