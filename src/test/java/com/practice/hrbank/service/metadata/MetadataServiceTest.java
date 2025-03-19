package com.practice.hrbank.service.metadata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.practice.hrbank.entity.Metadata;
import com.practice.hrbank.repository.MetadataRepository;
import com.practice.hrbank.service.MetadataService;
import com.practice.hrbank.storage.BinaryContentStorage;
import com.practice.hrbank.storage.EmployeesStorage;
import com.practice.hrbank.storage.local.LogFileStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class MetadataServiceTest {

  @InjectMocks
  private MetadataService metadataService;

  @Mock
  private MetadataRepository metadataRepository;

  @Mock
  private BinaryContentStorage binaryContentStorage;

  @Mock
  private EmployeesStorage employeesStorage;

  @Mock
  private LogFileStorage logFileStorage;

  @Test
  void createProfile_Success() throws IOException {
    // given
    MultipartFile profile = new MockMultipartFile("test.jpg", "test.jpg", "image/jpeg",
        "dummy".getBytes());
    Metadata metadata = new Metadata(profile.getName(), profile.getContentType(),
        profile.getSize());

    // when
    when(metadataRepository.save(any(Metadata.class))).thenReturn(metadata);
    Metadata result = metadataService.createProfile(profile);

    // then
    assertNotNull(result);
    assertEquals(profile.getName(), result.getName());
    verify(binaryContentStorage, times(1)).put(any(), any());
    verify(metadataRepository, times(1)).save(any(Metadata.class));
  }

  @Test
  void createEmployeesFile_Success() throws IOException {
    // given
    Long backupId = 1L;

    // when
    when(employeesStorage.save(backupId)).thenReturn(anyLong());
    Metadata result = metadataService.createEmployeesFile(backupId);

    // then
    assertNotNull(result);
    verify(employeesStorage, times(1)).save(backupId);
  }

  @Test
  void createErrorLogFile_Success() throws IOException {
    // given
    Instant time = Instant.now();
    String name = "backup_error_" + time.toString();
    String errorMessage = "Test error message";
    Path mockPath = mock(Path.class);

    try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
      // when
      when(logFileStorage.createLogFile(name)).thenReturn(mockPath);
      doNothing().when(logFileStorage).writeErrorToFile(mockPath, errorMessage);
      mockedFiles.when(() -> Files.size(mockPath)).thenReturn(1024L);

      Metadata metadata = metadataService.createErrorLogFile(time, errorMessage);

      // then
      assertEquals(name, metadata.getName());
      assertEquals("text/log", metadata.getContentType());
      assertEquals(1024L, metadata.getSize());
      verify(logFileStorage).createLogFile(name);
      verify(logFileStorage).writeErrorToFile(mockPath, errorMessage);
    }
  }

  @Test
  void findById_Success() {
    // given
    Long id = 1L;
    Metadata metadata = new Metadata("test.jpg", "image/jpeg", 1024L);

    // when
    when(metadataRepository.findById(id)).thenReturn(Optional.of(metadata));
    Metadata result = metadataService.findById(id);

    // then
    assertNotNull(result);
    assertEquals(metadata.getName(), result.getName());
    verify(metadataRepository, times(1)).findById(id);
  }

  @Test
  void findById_NotFoundId() {
    // given
    Long id = 1L;

    // when
    when(metadataRepository.findById(id)).thenReturn(Optional.empty());

    // then
    assertThrows(NoSuchElementException.class, () -> metadataService.findById(id));
    verify(metadataRepository, times(1)).findById(id);
  }
}