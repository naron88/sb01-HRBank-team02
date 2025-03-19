package com.practice.hrbank.service;

import com.practice.hrbank.dto.employee.EmployeeCreateRequest;
import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.dto.employee.EmployeeUpdateRequest;
import com.practice.hrbank.entity.Department;
import com.practice.hrbank.entity.Employee;
import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.mapper.EmployeeMapper;
import com.practice.hrbank.repository.DepartmentRepository;
import com.practice.hrbank.repository.EmployeeRepository;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;

  private final MetadataService metadataService;
  private final ChangeLogService changeLogService;

  private final DepartmentRepository departmentRepository;

  @Transactional
  public EmployeeDto create(EmployeeCreateRequest request, MultipartFile file) throws IOException {
    Metadata profile = file != null ? metadataService.createProfile(file) : null;
    Department department = departmentRepository.findById(request.departmentId())
        .orElseThrow(() -> new NoSuchElementException("Department with id " + request.departmentId() + " not found"));

    // TODO: 사번 패턴 적용해서 할당
    // TODO: 변경 이력도 생성
    Employee employee = new Employee(
        request.name(),
        request.email(),
        "employeeNumber",
        request.position(),
        request.hireDate(),
        profile,
        department
    );

    return employeeMapper.toDto(
        employeeRepository.save(employee)
    );
  }

  public List<EmployeeDto> searchEmployee() {
    return null;
  }

  public EmployeeDto findById() {
    return null;
  }

  @Transactional
  public EmployeeDto update(Long id, EmployeeUpdateRequest request, MultipartFile file) throws IOException {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));

    // TODO: 유효성 검증 로직 추가
    if (request.name() != null) {
      employee.updateName(request.name());
    }
    if (request.email() != null) {
      employee.updateEmail(request.email());
    }
    if (request.position() != null) {
      employee.updatePosition(request.position());
    }
    if (request.departmentId() != null) {
      Department department = departmentRepository.findById(request.departmentId())
          .orElse(null);
      employee.updateDepartment(department);
    }
    if (file != null) {
      Metadata profile = metadataService.createProfile(file);
      employee.updateProfile(profile);
    }
    if (request.status() != null) {
      employee.updateStatus(request.status());
    }

    // TODO: 변경 이력도 생성

    return employeeMapper.toDto(employee);
  }

  @Transactional
  public void delete(Long id) {
    if (!employeeRepository.existsById(id)) {
      throw new NoSuchElementException("Employee with id " + id + " not found");
    }

    employeeRepository.deleteById(id);
  }
}
