package com.practice.hrbank.repository;

import com.practice.hrbank.entity.Employee;
import com.practice.hrbank.entity.QEmployee;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {

  private final JPAQueryFactory queryFactory;
  private final QEmployee employee = QEmployee.employee;

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
      Pageable pageable) {

    BooleanBuilder predicate = new BooleanBuilder();

    if (nameOrEmail != null) {
      boolean isEmail = nameOrEmail.contains("@") && nameOrEmail.contains(".");

      if (isEmail) {
        predicate.and(employee.email.containsIgnoreCase(nameOrEmail));
      } else {
        predicate.and(employee.name.containsIgnoreCase(nameOrEmail));
      }
    }
    if (employeeNumber != null) {
      predicate.and(employee.employeeNumber.containsIgnoreCase(employeeNumber));
    }
    if (departmentName != null) {
      predicate.and(employee.department.name.containsIgnoreCase(departmentName));
    }
    if (position != null) {
      predicate.and(employee.position.containsIgnoreCase(position));
    }
    if (hireDateFrom != null || hireDateTo != null) {
      predicate.and(employee.hireDate.between(hireDateFrom, hireDateTo));
    }
    if (status != null) {
      predicate.and(employee.status.eq(status));
    }

    if (idAfter != null) {
      predicate.and(employee.id.gt(idAfter));
    }

    if (cursor != null) {
      Long cursorId = decodeCursor(cursor);
      predicate.and(employee.id.gt(cursorId));
    }

    JPAQuery<Employee> query = queryFactory.selectFrom(employee)
        .where(predicate)
        .orderBy(employee.name.asc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Employee> employees = query.fetch();

    return new PageImpl<>(employees, pageable, query.fetchCount());
  }

  private Long decodeCursor(String cursor) {
    byte[] decodedBytes = Base64.getDecoder().decode(cursor);
    return Long.parseLong(new String(decodedBytes));
  }
}

