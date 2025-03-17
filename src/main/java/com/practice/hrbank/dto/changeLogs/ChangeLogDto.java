package com.practice.hrbank.dto.changeLogs;

public record ChangeLogDto(
        Integer id,
        String type,
        String employeeNumber,
        String memo,
        String ipAddress,
        String at
) {
}
