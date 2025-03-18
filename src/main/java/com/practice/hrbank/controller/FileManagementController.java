package com.practice.hrbank.controller;

import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.service.MetadataService;
import com.practice.hrbank.storage.EmployeesStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/files")
public class FileManagementController {

  private final MetadataService metadataService;
  private final EmployeesStorage employeesStorage;

  @GetMapping("/{id}/download")
  public ResponseEntity<?> download(
    @PathVariable("id") Long metadataId) {
    Metadata metadata = metadataService.findById(metadataId);
    return employeesStorage.download(metadata);
  }
}
