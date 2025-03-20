package com.practice.hrbank.service.backup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.practice.hrbank.dto.backup.BackupDto;
import com.practice.hrbank.entity.Backup;
import com.practice.hrbank.entity.Employee;
import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.mapper.BackupMapper;
import com.practice.hrbank.repository.BackupRepository;
import com.practice.hrbank.repository.EmployeeRepository;
import com.practice.hrbank.service.BackupService;
import com.practice.hrbank.service.MetadataService;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BackupServiceCreateTest {

  @Mock
  private BackupRepository backupRepository;
  @Mock
  private BackupMapper backupMapper;
  @Mock
  private MetadataService metadataService;
  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private BackupService backupService;

  @BeforeEach
  void setUp() {
    when(backupRepository.save(any(Backup.class))).thenAnswer(
        invocation -> invocation.getArgument(0));

    when(backupMapper.toDto(any(Backup.class))).thenAnswer(invocation -> {
      Backup b = invocation.getArgument(0);
      return new BackupDto(
          b.getId(),
          b.getWorker(),
          b.getStartedAt(),
          b.getEndedAt(),
          b.getStatus(),
          b.getFile() != null ? b.getFile().getId() : null
      );
    });
  }

  @Test
  void 생성_FAILED() throws IOException {
    // given
    when(metadataService.createEmployeesFile(any())).thenThrow(new IOException("Error"));

    // when
    BackupDto result = backupService.create("127.0.0.1");

    // then
    assertThat(result).isNotNull();
    assertThat(result.status()).isEqualTo(Backup.Status.FAILED);
  }

  @Test
  void 생성_COMPLETED() throws IOException {
    // given
    when(employeeRepository.findByUpdatedAtGreaterThan(any()))
        .thenReturn(Optional.of(mock(Employee.class)));

    when(metadataService.createEmployeesFile(any())).thenReturn(new Metadata());

    // when
    BackupDto result = backupService.create("127.0.0.1");

    // then
    assertThat(result).isNotNull();
    assertThat(result.status()).isEqualTo(Backup.Status.COMPLETED);
  }

  @Test
  void 생성_SKIPPED() throws IOException {
    // given
    when(backupRepository.findFirstByStatusOrderByStartedAtDesc(Backup.Status.COMPLETED))
        .thenReturn(Optional.of(
            new Backup(new Metadata(), Backup.Status.COMPLETED, Instant.now(), Instant.now(),
                "127.0.0.1")));

    // when
    BackupDto result = backupService.create("127.0.0.1");

    // then
    assertThat(result).isNotNull();
    assertThat(result.status()).isEqualTo(Backup.Status.SKIPPED);
  }
}