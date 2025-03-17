package com.practice.hrbank.storage;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.Metadata;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface EmployeesStorage {

  EmployeeDto save(EmployeeDto employeeDto);

  Optional<EmployeeDto> findById(Long id);

  List<EmployeeDto> findAll();

  void deleteById(Long id);

  ResponseEntity<?> download(Metadata metadata);

}
