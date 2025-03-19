package com.practice.hrbank.service;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.repository.MetadataRepository;
import com.practice.hrbank.storage.EmployeesStorage;
import com.practice.hrbank.storage.BinaryContentStorage;
import com.practice.hrbank.storage.local.LogFileStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MetadataService {

  private final MetadataRepository metadataRepository;
  private final BinaryContentStorage binaryContentStorage;
  private final EmployeesStorage employeesStorage;
  private final LogFileStorage logFileStorage;

  public Metadata createProfile(MultipartFile profile) throws IOException {
    Metadata metadata = new Metadata(profile.getName(), profile.getContentType(), profile.getSize());
    binaryContentStorage.put(metadata.getId(), profile.getBytes());
    metadataRepository.save(metadata);
    return metadata;
  }

  public Metadata createEmployeesFile(Long backUpId) throws IOException {
    String name = "employee_backup_" + backUpId;
    Long size = employeesStorage.save(backUpId);
    return new Metadata(name, "text/csv", size);
  }

  public Metadata createErrorLogFile(Instant time, String errorMessage) throws IOException {
    String name = "backup_error_" + time.toString();
    Path logFilePath = logFileStorage.createLogFile(name);
    logFileStorage.writeErrorToFile(logFilePath, errorMessage);
    long size = Files.size(logFilePath);
    return new Metadata(name, "text/log", size);
  }

  public Metadata findById(Long id) {
    return metadataRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Metadata not found. id: " + id));
  }
}
