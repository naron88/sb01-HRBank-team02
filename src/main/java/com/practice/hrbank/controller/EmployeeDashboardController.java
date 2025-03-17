package com.practice.hrbank.controller;

import com.practice.hrbank.dto.dashboard.EmployeeDistributionDto;
import com.practice.hrbank.dto.dashboard.EmployeeTrendDto;
import com.practice.hrbank.service.DashboardService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeDashboardController {

  private final DashboardService dashboardService;

  @GetMapping("/stats/trend")
  ResponseEntity<List<EmployeeTrendDto>> getEmployeeTrend(
      @RequestParam(required = false) LocalDate from,
      @RequestParam(required = false) LocalDate to,
      @RequestParam(defaultValue = "month") String unit
  ) {
    List<EmployeeTrendDto> employeeTrendDtoList = dashboardService.getEmployeeTrend(from, to, unit);
    return ResponseEntity.ok(employeeTrendDtoList);
  }

  @GetMapping("/stats/distribution")
  ResponseEntity<List<EmployeeDistributionDto>> getEmployeeDistribution(
      @RequestParam(defaultValue = "department") String department,
      @RequestParam(defaultValue = "ACTIVE") String active
  ) {
    List<EmployeeDistributionDto> employeeDistributionDtoList = dashboardService.getEmployeeDistribution(department, active);
    return ResponseEntity.ok(employeeDistributionDtoList);
  }

  @GetMapping("/count")
  ResponseEntity<Long> countEmployees(
      @RequestParam(required = false) String status,
      @RequestParam(required = false) LocalDate fromDate,
      @RequestParam(required = false) LocalDate toDate
  ) {
    Long count = dashboardService.countEmployees(status, fromDate, toDate);
    return ResponseEntity.ok(count);
  }
}
