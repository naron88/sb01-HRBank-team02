package com.practice.hrbank.dto.changeLog;

public record ChangeLogRequestDto(
        String employeeNumber,
        String type,
        String memo,
        String ipAddress,
        String atFrom,
        String atTo,
        String idAfter,
        String cursor,
        String size,
        String sortField,
        String sortDirection
) {

}
