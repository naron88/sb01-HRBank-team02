package com.practice.hrbank.service;

import com.practice.hrbank.dto.DepartmentCreateRequest;
import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.ChangeLog;
import com.practice.hrbank.repository.ChangeLogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ChangeLogServiceTest {

    @Autowired
    ChangeLogRepository changeLogRepository;
    @Autowired
    ChangeLogService changeLogService;

    @BeforeEach
    public void setUp() {
        IntStream.range(1, 11).forEach(i -> {
            EmployeeDto beforeEmployee = new EmployeeDto(
                    (long) i, "User" + i, "user" + i + "@example.com", "EMP" + i,
                    1L, "HR", "Developer", LocalDate.now(), null, null
            );
            EmployeeDto afterEmployee = new EmployeeDto(
                    (long) i, "UpdatedUser" + i, "updateduser" + i + "@example.com", "EMP" + i,
                    1L, "HR", "Senior Developer", LocalDate.now(), null, null
            );
            DepartmentCreateRequest request = new DepartmentCreateRequest(
                    beforeEmployee,
                    afterEmployee,
                    "127.0.0." + i,
                    "Test Memo " + i,
                    ChangeLog.Type.UPDATED
            );
            changeLogService.save(request);
        });
    }

    @AfterEach
    public void tearDown() {
        changeLogRepository.deleteAll();
    }

    @Test
    public void 저장_테스트() {
        long count = changeLogRepository.count();
        assertEquals(10, count, "10개의 ChangeLog가 저장되어야 합니다.");
    }

}