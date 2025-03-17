package com.practice.hrbank.service;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.repository.EmployeeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  public EmployeeDto create() {
    return null;
  }

  public List<EmployeeDto> searchEmployee() {
    return null;
  }

  public EmployeeDto findById() {
    return null;
  }

  public EmployeeDto update() {
   return null;
  }

  public void delete() {

  }
}
