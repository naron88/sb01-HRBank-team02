package com.practice.hrbank.service;

import com.practice.hrbank.dto.dashboard.EmployeeDistributionDto;
import com.practice.hrbank.dto.dashboard.EmployeeTrendDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

  // private final EmployeeRepository employeeRepository;

  public List<EmployeeTrendDto> getEmployeeTrend(LocalDate from, LocalDate to, String unit) {
    return null;
  }
  public List<EmployeeDistributionDto> getEmployeeDistribution(String department, String active) {
    return null;
  }

  public long countEmployees(String status, LocalDate fromDate, LocalDate toDate) {
    return 1;
  }
}
