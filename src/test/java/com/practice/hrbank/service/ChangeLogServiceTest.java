package com.practice.hrbank.service;

import com.practice.hrbank.dto.DepartmentCreateRequest;
import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.ChangeLog;
import com.practice.hrbank.repository.ChangeLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Spring 컨텍스트 없이 실행
class ChangeLogServiceTest {

    @Mock
    private ChangeLogRepository changeLogRepository;

    @InjectMocks
    private ChangeLogService changeLogService;

    private List<DepartmentCreateRequest> testRequests;

    @BeforeEach
    void setUp() {
        testRequests = IntStream.range(1, 11)
                .mapToObj(i -> new DepartmentCreateRequest(
                        new EmployeeDto((long) i, "User" + i, "user" + i + "@example.com", "EMP" + i,
                                1L, "HR", "Developer", LocalDate.now(), null, null),
                        new EmployeeDto((long) i, "UpdatedUser" + i, "updateduser" + i + "@example.com", "EMP" + i,
                                1L, "HR", "Senior Developer", LocalDate.now(), null, null),
                        "127.0.0." + i,
                        "Test Memo " + i,
                        ChangeLog.Type.UPDATED
                )).toList();
    }

    @Test
    void 저장_테스트() {
        // Mock 설정: 저장할 때 아무 동작도 하지 않도록 설정
        when(changeLogRepository.save(any(ChangeLog.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 10개의 데이터를 저장
        testRequests.forEach(changeLogService::save);

        // Repository의 save()가 10번 호출되었는지 검증
        verify(changeLogRepository, times(10)).save(any(ChangeLog.class));
    }

    @Test
    void 로그_갯수_테스트() {
        when(changeLogRepository.count()).thenReturn(10L);

        long count = changeLogRepository.count();
        assertEquals(10, count, "10개의 ChangeLog가 저장되어야 합니다.");

        verify(changeLogRepository, times(1)).count();
    }
}