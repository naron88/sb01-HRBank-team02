package com.practice.hrbank.repository;

import com.practice.hrbank.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {

    @Query("SELECT e.employeeNumber FROM Employee e WHERE e.employeeNumber LIKE :yearPrefix ORDER BY e.employeeNumber DESC")
    String findLatestEmployeeNumberByYear(@Param("yearPrefix") int yearPrefix);
}
