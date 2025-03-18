package com.practice.hrbank.storage.local;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.Employee;
import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.storage.EmployeesStorage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "hrbank.storage.type", havingValue = "local")
public class LocalEmployeesStorage implements EmployeesStorage {

  private final Path root;
  private final Map<Long, EmployeeDto> store;

  public LocalEmployeesStorage(@Value("${hrbank.storage.local.paths.employees-path}") String root) {
    this.root = Paths.get(root);
    try {
      Files.createDirectories(this.root);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.store = loadEmployeesFromCsv();
  }

  @Override
  public EmployeeDto save(EmployeeDto employeeDto) {
    store.put(employeeDto.id(), employeeDto);
    saveEmployeesToCsv();
    return employeeDto;
  }

  @Override
  public Optional<EmployeeDto> findById(Long id) {
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public List<EmployeeDto> findAll() {
    return new ArrayList<>(store.values());
  }

  @Override
  public void deleteById(Long id) {
    store.remove(id);
    saveEmployeesToCsv();
  }

  @Override
  public ResponseEntity<Resource> download(Metadata metadata) {
    Path filePath = resolvePath(metadata.getId());
    org.springframework.core.io.Resource resource = new FileSystemResource(filePath);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + metadata.getName() + "\"")
        .header(HttpHeaders.CONTENT_TYPE, metadata.getContentType())
        .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(metadata.getSize()))
        .body(resource);
  }

  private Path resolvePath(Long id) {
    return root.resolve(id.toString());
  }


  private void saveEmployeesToCsv() {
    Path filePath = root.resolve("employees_backup.csv");

    try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING)) {
      writer.write("ID,직원번호,이름,이메일,부서,직급,입사일,상태");
      writer.newLine();

      store.values().forEach(emp -> {
        try {
          writer.write(
              String.join(",",
                  emp.id().toString(),
                  emp.employeeNumber(),
                  emp.name(),
                  emp.email(),
                  emp.departmentName(),
                  emp.position(),
                  emp.hireData().toString(),
                  emp.status().name()));
          writer.newLine();
        } catch (IOException e) {
          throw new RuntimeException("CSV 저장 중 오류 발생", e);
        }
      });
      writer.flush();
    } catch (IOException e) {
      throw new RuntimeException("CSV 저장 실패", e);
    }
  }

  private Map<Long, EmployeeDto> loadEmployeesFromCsv() {
    Map<Long, EmployeeDto> map = new TreeMap<>();
    Path filePath = root.resolve("employees_backup.csv");

    if (Files.notExists(filePath)) {
      return map;
    }
    try (BufferedReader reader = Files.newBufferedReader(filePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .build())) {

      for (CSVRecord record : csvParser) {
        EmployeeDto employeeDto = new EmployeeDto(
            Long.parseLong(record.get("ID")),
            record.get("이름"),
            record.get("이메일"),
            record.get("직원번호"),
            null,
            record.get("부서"),
            record.get("직급"),
            LocalDate.parse(record.get("입사일")),
            Employee.Status.valueOf(record.get("상태")),
            null
            );
        map.put(employeeDto.id(), employeeDto);
      }
    } catch (IOException e) {
      throw new RuntimeException("CSV 파일 읽기 실패", e);
    }
    return map;
  }
}
