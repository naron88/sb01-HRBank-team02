package com.practice.hrbank.dto.backup;

public record CursorPageResponseBackupDto(
//    List<EmployeeDto> content,
    String nextCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    boolean hasNext
) {

}
