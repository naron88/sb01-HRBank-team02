package com.practice.hrbank.storage.local;

import com.practice.hrbank.entity.Employee;
import com.practice.hrbank.repository.EmployeeRepository;
import com.practice.hrbank.storage.EmployeesStorage;
import jakarta.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "hrbank.storage.type", havingValue = "local")
public class LocalEmployeesStorage implements EmployeesStorage {

  private final Path root;
  private final EmployeeRepository employeeRepository;

  @Autowired
  public LocalEmployeesStorage(@Value("${hrbank.storage.local.paths.employees-path}") String root,
      EmployeeRepository employeeRepository) throws IOException {
    this.root = Paths.get(root);
    this.employeeRepository = employeeRepository;
  }

  @PostConstruct
  public void init() throws IOException {
    Files.createDirectories(root);
  }

  @Override
  public Long save(Long backupId) throws IOException {
    String backupFileName = "employee_backup_" + backupId + ".csv";
    Path filePath = root.resolve(backupFileName);
    List<Employee> employees = employeeRepository.findAll();

    saveEmployeesToCsv(filePath, employees);
    return Files.size(filePath);
  }

  @Override
  public ResponseEntity<Resource> download(Long backupId) {
    String backupFileName = "employee_backup_" + backupId + ".csv";
    Path filePath = root.resolve(backupFileName);
    if (Files.notExists(filePath)) {
      throw new RuntimeException("파일이 존재하지 않습니다.");
    }
    org.springframework.core.io.Resource resource = new FileSystemResource(filePath);
    return ResponseEntity.ok().body(resource);
  }

  private void saveEmployeesToCsv(Path filePath, List<Employee> employees) throws IOException {
    try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING)) {
      writer.write("ID,직원번호,이름,이메일,부서,직급,입사일,상태");
      writer.newLine();

      for (Employee emp : employees) {
        writer.write(
            String.join(",",
                emp.getId().toString(),
                emp.getEmployeeNumber(),
                emp.getName(),
                emp.getEmail(),
                emp.getDepartment().getName(),
                emp.getPosition(),
                emp.getHireDate().toString(),
                emp.getStatus().name()));
        writer.newLine();
      }
      writer.flush();
    }
  }
}
