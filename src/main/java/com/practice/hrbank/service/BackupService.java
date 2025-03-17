package com.practice.hrbank.service;

import com.practice.hrbank.dto.backup.BackupDto;
import com.practice.hrbank.entity.Backup;
import com.practice.hrbank.mapper.BackupMapper;
import com.practice.hrbank.repository.BackupRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackupService {

  private final BackupRepository backupRepository;
  private final EmployeeService employeeService;
  private final BackupMapper backupMapper;

  public BackupDto create() {
    Backup backup = new Backup();
    backupRepository.save(backup);
    return backupMapper.toDto(backup);
  }

  public List<BackupDto> findAll() {
    // TODO : 필터링 로직 추가
    List<Backup> backups = backupRepository.findAll();
    return backups.stream().map(backupMapper::toDto).toList();
  }

  public BackupDto findLatest() {
    Backup lastBackup = backupRepository.findFirstByOrderByStartedAtDesc()
        .orElseThrow(() -> new NoSuchElementException("No backup found"));
    return backupMapper.toDto(lastBackup);
  }

  private boolean isChanged(Backup lastBackup) {
    return false;
  }


}
