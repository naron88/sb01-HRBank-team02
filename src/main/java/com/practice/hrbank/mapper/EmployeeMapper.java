package com.practice.hrbank.mapper;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.Employee;

public class EmployeeMapper {

  public EmployeeDto toDto(Employee employee) {
    return new EmployeeDto(
        employee.getId(),
        employee.getName(),
        employee.getEmail(),
        employee.getEmployeeNumber(),
        employee.getDepartment().getId(),
        employee.getDepartment().getName(),
        employee.getPosition(),
        employee.getHireDate(),
        employee.getStatus(),
        employee.getProfileImage().getId()
    );
  }
}
