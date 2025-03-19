package com.practice.hrbank.storage;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.Metadata;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface EmployeesStorage {

  Long save(Long backupId) throws IOException;

  ResponseEntity<?> download(Long backupId);

}
