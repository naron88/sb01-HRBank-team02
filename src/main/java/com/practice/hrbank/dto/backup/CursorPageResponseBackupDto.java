package com.practice.hrbank.dto.backup;

import org.springframework.data.domain.Page;

public record CursorPageResponseBackupDto(
    Page<BackupDto> content,
    String nextCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    boolean hasNext
) {

}
