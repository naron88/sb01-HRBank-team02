package com.practice.hrbank.service;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.repository.MetadataRepository;
import com.practice.hrbank.storage.EmployeesStorage;
import com.practice.hrbank.storage.BinaryContentStorage;
import java.io.IOException;
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

  public Metadata createProfile(MultipartFile profile) throws IOException {
    Metadata metadata = new Metadata(profile.getName(), profile.getContentType(), profile.getSize());
    binaryContentStorage.put(metadata.getId(), profile.getBytes());
    metadataRepository.save(metadata);
    return metadata;
  }

  public EmployeeDto createEmployeesFile(EmployeeDto employeeDto) {
    employeesStorage.save(employeeDto);
    return employeeDto;
  }

  public Metadata findById(Long id) {
    return metadataRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Metadata not found. id: " + id));
  }
}
