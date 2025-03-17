package com.practice.hrbank.dto.dashboard;

import java.time.LocalDate;

public record EmployeeDistributionDto(
    String groupKey,
    int count,
    double percentage
) {

}
