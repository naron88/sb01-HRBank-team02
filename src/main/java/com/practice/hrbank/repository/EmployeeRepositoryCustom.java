package com.practice.hrbank.repository;

import com.practice.hrbank.entity.Employee;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepositoryCustom {
  Page<Employee> findAllWithFilters(
      String nameOrEmail,
      String employeeNumber,
      String departmentName,
      String position,
      LocalDate hireDateFrom,
      LocalDate hireDateTo,
      Employee.Status status,
      Long idAfter,
      String cursor,
      Pageable pageable);
}
