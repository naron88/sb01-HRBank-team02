package com.practice.hrbank.repository;

import com.practice.hrbank.entity.Employee;
import java.time.Instant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  Optional<Employee> findByUpdatedAtGreaterThan(Instant lastBatchTime); //최근에 수정된 직원 조회
}
