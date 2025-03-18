package com.practice.hrbank.service;

import com.practice.hrbank.dto.backup.BackupDto;
import com.practice.hrbank.dto.backup.CursorPageResponseBackupDto;
import com.practice.hrbank.entity.Backup;
import com.practice.hrbank.mapper.BackupMapper;
import com.practice.hrbank.repository.BackupRepository;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackupService {

  private final BackupRepository backupRepository;
  private final BackupMapper backupMapper;

  public BackupDto create() {
    Backup backup = new Backup();
    backupRepository.save(backup);
    return backupMapper.toDto(backup);
  }

  public CursorPageResponseBackupDto findAll(String worker, String status,
      Instant startedAtFrom, Instant startedAtTo, Long idAfter, String cursor,
      Integer size, String sortField, String sortDirection) {

    idAfter = decodeCursor(cursor);

    Specification<Backup> spec = Specification.where(getSpec(worker, status, startedAtFrom, startedAtTo, idAfter));
    Pageable pageable = createPageable(size, sortField, sortDirection);
    Page<Backup> backups = backupRepository.findAll(spec, pageable);

    Long nextIdAfter = null;
    String nextCursor = null;

    if (backups.hasContent()) {
      List<Backup> content = backups.getContent();
      nextIdAfter = content.get(content.size() - 1).getId();
      nextCursor = Base64.getEncoder().encodeToString(nextIdAfter.toString().getBytes());
    }

    return new CursorPageResponseBackupDto(
        backups.map(backupMapper::toDto),
        nextCursor,
        nextIdAfter,
        backups.getSize(),
        backups.getTotalElements(),
        backups.hasNext()
    );  }

  public BackupDto findLatest() {
    Backup lastBackup = backupRepository.findFirstByOrderByStartedAtDesc()
        .orElseThrow(() -> new NoSuchElementException("No backup found"));
    return backupMapper.toDto(lastBackup);
  }

  private boolean isChanged(Backup lastBackup) {
    return false;
  }

  private Specification<Backup> getSpec(String worker, String status, Instant startedAtFrom,
      Instant startedAtTo, Long idAfter) {
    Specification<Backup> spec = Specification.where(null);
    if (worker != null && !worker.isEmpty()) {
      spec = spec.and((root, query, cb) ->
          cb.like(root.get("worker"), "%" + worker + "%"));
    }
    if (status != null && !status.isEmpty()) {
      spec = spec.and((root, query, cb) ->
          cb.equal(root.get("status"), status));
    }

    if (startedAtFrom != null && startedAtTo == null) {
      spec = spec.and((root, query, cb) ->
          cb.between(root.get("startTime"), startedAtFrom, Instant.now()));
    } else if (startedAtFrom == null && startedAtTo != null) {
      spec = spec.and((root, query, cb) ->
          cb.between(root.get("startTime"), Instant.EPOCH, startedAtTo));
    }
    if (startedAtFrom != null && startedAtTo != null) {
      spec = spec.and((root, query, cb) ->
          cb.between(root.get("startTime"), startedAtFrom, startedAtTo));
    }

    if (idAfter != null) {
      spec = spec.and((root, query, criteriaBuilder) ->
          criteriaBuilder.greaterThan(root.get("id"), idAfter));
    }

    return spec;
  }

  private Pageable createPageable(Integer size, String sortField, String sortDirection) {
    if (size == null || size <= 0) {
      size = 10;
    }

    Sort sort;
    if (sortField != null && !sortField.isEmpty()) {
      if ("desc".equalsIgnoreCase(sortDirection)) {
        sort = Sort.by(Sort.Direction.DESC, sortField);
      } else {
        sort = Sort.by(Sort.Direction.ASC, sortField);
      }
    } else {
      sort = Sort.by(Direction.DESC, "startedAt");
    }

    return PageRequest.of(0, size, sort);
  }

  private Long decodeCursor(String cursor) {
    if (cursor != null) {
      try {
        String decoded = new String(Base64.getDecoder().decode(cursor));
        return Long.parseLong(decoded);
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid cursor format");
      }
    }
    return null;
  }
}
