package com.practice.hrbank.storage.local;

import static org.junit.jupiter.api.Assertions.*;
import com.practice.hrbank.entity.Department;
import com.practice.hrbank.entity.Employee;
import com.practice.hrbank.entity.Employee.Status;
import com.practice.hrbank.entity.Metadata;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "hrbank.storage.local.paths.employees-path=target/test-files")
public class LocalEmployeesStorageTest {

  @Autowired
  private LocalEmployeesStorage localEmployeesStorage;

  private Path testDirectory;

  @BeforeEach
  public void setUp() throws IOException {
    testDirectory = Path.of("target", "test-files");
    if (Files.notExists(testDirectory)) {
      Files.createDirectories(testDirectory);
    }
  }

  @Test
  public void save_Success() throws IOException {
    // given
    Employee emp1 = new Employee(1L, "test", "test@gmail.com", "12", "Manager", LocalDate.now(), new Metadata("test", "test", 1L), new Department("test", "test",
        LocalDate.now()), Status.ACTIVE);
    Employee emp2 = new Employee(2L, "test2", "test2@gmail.com", "122", "Manager2", LocalDate.now(), new Metadata("test2", "test2", 2L), new Department("test2", "test2",
        LocalDate.now()), Status.ACTIVE);
    List<Employee> employees = Arrays.asList(emp1, emp2);
    Long backupId = 3L;
    Path filePath = testDirectory.resolve("employee_backup_" + backupId + ".csv");

    // when
    Long fileSize = localEmployeesStorage.save(backupId, employees);

    // then
    assertTrue(Files.exists(filePath));
    assertEquals(fileSize, Files.size(filePath));



  }

  @Test
  public void download_Success() throws IOException {
    // given
    long backupId = 3L;
    Path filePath = testDirectory.resolve("employee_backup_" + backupId + ".csv");
    Metadata metadata = new Metadata("employee_backup_" + backupId + ".csv", "text/csv", Files.size(filePath));

    // when
    ResponseEntity<Resource> responseEntity = localEmployeesStorage.download(metadata);

    // then
    assertNotNull(responseEntity.getBody());
    assertInstanceOf(InputStreamResource.class, responseEntity.getBody());
  }
}