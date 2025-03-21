package com.practice.hrbank.repository;

import com.practice.hrbank.entity.Employee;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

  private final EmployeeRepository employeeRepository;

  @Override
  public Page<Employee> findAllWithFilters(
      String nameOrEmail,
      String employeeNumber,
      String departmentName,
      String position,
      LocalDate hireDateFrom,
      LocalDate hireDateTo,
      Employee.Status status,
      Long idAfter,
      String cursor,
      Pageable pageable
  ) {
    Specification<Employee> specification = (root, query, criteriaBuilder) -> {
      Predicate predicate = criteriaBuilder.conjunction();

      if (nameOrEmail != null) {
        boolean isEmail = nameOrEmail.contains("@") && nameOrEmail.contains(".");
        if (isEmail) {
          predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + nameOrEmail.toLowerCase() + "%"));
        } else {
          predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + nameOrEmail.toLowerCase() + "%"));
        }
      }

      if (employeeNumber != null) {
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeNumber")), "%" + employeeNumber.toLowerCase() + "%"));
      }

      if (departmentName != null) {
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("department").get("name")), "%" + departmentName.toLowerCase() + "%"));
      }

      if (position != null) {
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("position")), "%" + position.toLowerCase() + "%"));
      }

      if (hireDateFrom != null || hireDateTo != null) {
        if (hireDateFrom != null && hireDateTo != null) {
          predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("hireDate"), hireDateFrom, hireDateTo));
        } else if (hireDateFrom != null) {
          predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("hireDate"), hireDateFrom));
        } else if (hireDateTo != null) {
          predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("hireDate"), hireDateTo));
        }
      }

      if (status != null) {
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
      }

      if (idAfter != null) {
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("id"), idAfter));
      }

      if (cursor != null) {
        Long cursorId = decodeCursor(cursor);
        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThan(root.get("id"), cursorId));
      }

      return predicate;
    };

    Page<Employee> employees = employeeRepository.findAll(specification, pageable);

    return employees;
  }

  private Long decodeCursor(String cursor) {
    byte[] decodedBytes = Base64.getDecoder().decode(cursor);
    return Long.parseLong(new String(decodedBytes));
  }
}
