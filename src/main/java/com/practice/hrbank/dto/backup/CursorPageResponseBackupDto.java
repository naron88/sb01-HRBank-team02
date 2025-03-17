package com.practice.hrbank.dto.backup;

import com.practice.hrbank.entity.Employee;
import java.util.List;

public record CursorPageResponseBackupDto(
    List<Employee> content,
    String nextCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    boolean hasNext
) {

}
