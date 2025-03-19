package com.practice.hrbank.dto;

import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.ChangeLog;

public record DepartmentCreateRequest(
        EmployeeDto beforeEmployeeDto,
        EmployeeDto afterEmployeeDto,
        String ipAddress,
        String memo,
        ChangeLog.Type changeType
) {
}
