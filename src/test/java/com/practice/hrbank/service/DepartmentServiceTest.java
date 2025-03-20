package com.practice.hrbank.service;

import com.practice.hrbank.entity.Department;
import com.practice.hrbank.repository.DepartmentRepository;
import com.practice.hrbank.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    public void setUp() {
        department = new Department("HR", "Human Resources", null, 0);
    }

    @Test
    public void testDeleteDepartment_Success() {
        // Given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(departmentRepository.save(department)).thenReturn(department);

        // When
        boolean result = departmentService.deleteDepartment(1L);

        // Then
        assertTrue(result);
        verify(departmentRepository, times(1)).delete(department);
    }

    @Test
    public void testDeleteDepartment_DepartmentNotFound() {
        // Given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        boolean result = departmentService.deleteDepartment(1L);

        // Then
        assertFalse(result);
        verify(departmentRepository, times(0)).delete(any(Department.class));
    }

    @Test
    public void testDeleteDepartment_HasEmployees() {
        // Given
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));
        when(employeeRepository.existsByDepartmentId(anyLong())).thenReturn(true); // 직원이 소속된 부서일 경우

        // When
        boolean result = departmentService.deleteDepartment(1L);

        // Then
        assertFalse(result);
        verify(departmentRepository, times(0)).delete(any(Department.class)); // 삭제되지 않음
    }
}
