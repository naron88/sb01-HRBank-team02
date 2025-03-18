package com.practice.hrbank.service;

import com.practice.hrbank.dto.DepartmentDto;
import com.practice.hrbank.entity.Department;
import com.practice.hrbank.repository.DepartmentRepository;
import com.practice.hrbank.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 부서_등록_성공() {
        // Given
        DepartmentDto dto = new DepartmentDto(null, "IT", "IT 관련 부서", LocalDate.of(2023, 1, 1));
        Department department = new Department("IT", "IT 관련 부서", LocalDate.of(2023, 1, 1));

        when(departmentRepository.existsByName(dto.name())).thenReturn(false); // 중복 X
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        // When
        DepartmentDto created = departmentService.create(dto);

        // Then
        assertThat(created.name()).isEqualTo(dto.name());
        assertThat(created.description()).isEqualTo(dto.description());
        assertThat(created.establishedDate()).isEqualTo(dto.establishedDate());

        verify(departmentRepository, times(1)).existsByName(dto.name());
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void 중복된_이름으로_부서_등록_실패() {
        // Given
        DepartmentDto dto = new DepartmentDto(null, "IT", "IT 관련 부서", LocalDate.of(2023, 1, 1));

        when(departmentRepository.existsByName(dto.name())).thenReturn(true); // 중복 있음

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            departmentService.create(dto);
        });

        assertThat(exception.getMessage()).isEqualTo("부서 중복");
        verify(departmentRepository, times(1)).existsByName(dto.name());
        verify(departmentRepository, never()).save(any(Department.class)); // 저장 시도 X
    }
}
