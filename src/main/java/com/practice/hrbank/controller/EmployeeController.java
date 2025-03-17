package com.practice.hrbank.controller;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.service.EmployeeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<EmployeeDto> create() {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(employeeService.create());
  }

  @GetMapping
  public ResponseEntity<List<EmployeeDto>> findAll() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(employeeService.searchEmployee());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeDto> find(@PathVariable Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(employeeService.findById());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<EmployeeDto> update(@PathVariable Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(employeeService.update());
  }

}
