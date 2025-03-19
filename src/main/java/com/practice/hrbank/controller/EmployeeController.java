package com.practice.hrbank.controller;

import com.practice.hrbank.dto.employee.EmployeeCreateRequest;
import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.dto.employee.EmployeeUpdateRequest;
import com.practice.hrbank.service.EmployeeService;
import jakarta.validation.Valid;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
  private final EmployeeService employeeService;

  @PostMapping
  public ResponseEntity<EmployeeDto> create(@Valid @RequestPart EmployeeCreateRequest request,
      @RequestPart(value = "profile", required = false) MultipartFile file) throws IOException {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(employeeService.create(request, file));
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
        .body(employeeService.findById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    employeeService.delete(id);

    return ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<EmployeeDto> update(@PathVariable Long id,
      @RequestPart EmployeeUpdateRequest request,
      @RequestPart(value = "profile", required = false) MultipartFile file) throws IOException {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(employeeService.update(id, request, file));
  }

}
