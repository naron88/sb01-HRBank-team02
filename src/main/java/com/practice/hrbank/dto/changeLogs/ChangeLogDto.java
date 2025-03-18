package com.practice.hrbank.dto.changeLogs;

public record ChangeLogDto(
        Long id,
        String type,
        String employeeNumber,
        String memo,
        String ipAddress,
        String at
) {
}
