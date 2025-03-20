package com.practice.hrbank.service;

import com.practice.hrbank.dto.changeLog.ChangeLogCreateRequest;
import com.practice.hrbank.dto.employee.CursorPageResponseEmployeeDto;
import com.practice.hrbank.dto.employee.EmployeeCreateRequest;
import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.dto.employee.EmployeeUpdateRequest;
import com.practice.hrbank.entity.ChangeLog.Type;
import com.practice.hrbank.entity.Department;
import com.practice.hrbank.entity.Employee;
import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.mapper.EmployeeMapper;
import com.practice.hrbank.repository.DepartmentRepository;
import com.practice.hrbank.repository.EmployeeRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  public EmployeeDto create(EmployeeCreateRequest request, MultipartFile file, String ipAddress)
      throws IOException {
    validateDuplicateEmail(request.email());

    Metadata profile = file != null ? metadataService.createProfile(file) : null;
    Department department = departmentRepository.findById(request.departmentId())
        .orElseThrow(() -> new NoSuchElementException(
            "Department with id " + request.departmentId() + " not found"));
    String employeeNumber = generateEmployeeNumber();

    Employee employee = new Employee(
        request.name(),
        request.email(),
        employeeNumber,
        request.position(),
        request.hireDate(),
        profile,
        department
    );

    EmployeeDto employeeDto = employeeMapper.toDto(employeeRepository.save(employee));
    ChangeLogCreateRequest changeLogCreateRequest = new ChangeLogCreateRequest(
        null,
        employeeDto,
        ipAddress,
        request.memo(),
        Type.CREATED
    );
    changeLogService.save(changeLogCreateRequest);

    return employeeDto;
  }

  @Transactional(readOnly = true)
  public CursorPageResponseEmployeeDto<EmployeeDto> searchEmployee(String nameOrEmail,
      String employeeNumber, String departmentName,
      String position, LocalDate hireDateFrom, LocalDate hireDateTo, Employee.Status status,
      Long idAfter, String cursor, Integer size, String sortField, String sortDirection) {

    Sort.Direction direction = Sort.Direction.fromString(sortDirection);
    Pageable pageable = PageRequest.of(0, size, Sort.by(direction, sortField));
    Page<Employee> employeePage = employeeRepository.findAllWithFilters(
        nameOrEmail, employeeNumber, departmentName, position, hireDateFrom, hireDateTo, status,
        idAfter, cursor, pageable
    );

    List<EmployeeDto> content = employeeMapper.toDtoList(employeePage.getContent());
    EmployeeDto lastEmployee = content.isEmpty() ? null : content.get(content.size() - 1);

    String nextCursor = lastEmployee != null ? encodeCursor(lastEmployee.id()) : null;
    Long nextIdAfter = lastEmployee != null ? lastEmployee.id() : null;
    boolean hasNext = employeePage.hasNext();

    return new CursorPageResponseEmployeeDto<>(
        content,
        nextCursor,
        nextIdAfter,
        size,
        employeePage.getTotalElements(),
        hasNext
    );
  }

  @Transactional(readOnly = true)
  public EmployeeDto findById(Long id) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));

    return employeeMapper.toDto(employee);
  }

  @Transactional
  public EmployeeDto update(Long id, EmployeeUpdateRequest request, MultipartFile file,
      String ipAddress) throws IOException {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));
    Department department = departmentRepository.findById(request.departmentId())
        .orElse(null);

    EmployeeDto beforeEmployeeDto = employeeMapper.toDto(employee);

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
    if (department != null) {
      employee.updateDepartment(department);
    }
    if (file != null) {
      Metadata profile = metadataService.createProfile(file);
      employee.updateProfile(profile);
    }
    if (request.status() != null) {
      employee.updateStatus(request.status());
    }

    EmployeeDto afterEmployeeDto = employeeMapper.toDto(employee);
    ChangeLogCreateRequest changeLogCreateRequest = new ChangeLogCreateRequest(
        beforeEmployeeDto,
        afterEmployeeDto,
        ipAddress,
        request.memo(),
        Type.UPDATED
    );
    changeLogService.save(changeLogCreateRequest);

    return afterEmployeeDto;
  }

  @Transactional
  public void delete(Long id, String ipAddress) {
    Employee employee = employeeRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));

    EmployeeDto employeeDto = employeeMapper.toDto(employee);
    ChangeLogCreateRequest changeLogCreateRequest = new ChangeLogCreateRequest(
        employeeDto,
        null,
        ipAddress,
        "직원 삭제",
        Type.DELETED
    );
    changeLogService.save(changeLogCreateRequest);

    employeeRepository.deleteById(id);
  }

  public String generateEmployeeNumber() {
    int currentYear = LocalDate.now().getYear();
    String lastEmployeeNumber = employeeRepository.findLatestEmployeeNumberByYear(currentYear);

    if (lastEmployeeNumber == null) {
      return "EMP-" + currentYear + "-001";
    }

    int lastNumber = Integer.parseInt(
        lastEmployeeNumber.substring(lastEmployeeNumber.length() - 3));
    int newNumber = lastNumber + 1;

    return String.format("EMP-%d-%03d", currentYear, newNumber);
  }

  private String encodeCursor(Long id) {
    return Base64.getEncoder().encodeToString(id.toString().getBytes());
  }

  public void validateDuplicateEmail(String email) {
    employeeRepository.findAll()
        .forEach(employee -> employee.validateDuplicateEmail(email));
  }
}
