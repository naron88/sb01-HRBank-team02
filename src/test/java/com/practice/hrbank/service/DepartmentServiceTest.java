package com.practice.hrbank.service;

import com.practice.hrbank.dto.DepartmentDto;
import com.practice.hrbank.dto.DepartmentUpdateRequest;
import com.practice.hrbank.entity.Department;
import com.practice.hrbank.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        department = new Department(1L, "HR", "Human Resources", LocalDate.of(2020, 1, 1), 0);
        departmentDto = new DepartmentDto(1L, "HR", "Human Resources", LocalDate.of(2020, 1, 1), 0);
    }

    @Test
    void createDepartment_Success() {
        when(departmentRepository.existsByName(departmentDto.name())).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        DepartmentDto savedDto = departmentService.create(departmentDto);

        assertThat(savedDto).isNotNull().extracting(DepartmentDto::name).isEqualTo("HR");
        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void createDepartment_Fail_DuplicateName() {
        when(departmentRepository.existsByName(departmentDto.name())).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> departmentService.create(departmentDto));

        assertThat(exception.getMessage()).isEqualTo("부서 중복");
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void updateDepartment_Success() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        DepartmentUpdateRequest updateRequest = new DepartmentUpdateRequest("HR", "Updated Description", LocalDate.of(2021, 1, 1));

        DepartmentDto updatedDto = departmentService.update(1L, updateRequest);

        assertThat(updatedDto).isNotNull()
                .extracting(DepartmentDto::name, DepartmentDto::description)
                .containsExactly("HR", "Updated Description");
        verify(departmentRepository).findById(1L);
    }

    @Test
    void updateDepartment_Fail_NotFound() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.empty());
        DepartmentUpdateRequest updateRequest = new DepartmentUpdateRequest("HR", "Updated Description", LocalDate.of(2021, 1, 1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> departmentService.update(1L, updateRequest));

        assertThat(exception.getMessage()).isEqualTo("부서를 찾을 수 없습니다.");
    }

    @Test
    void updateDepartment_Success_UpdateNameOnly() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.findByName("New HR")).thenReturn(Optional.empty());

        DepartmentUpdateRequest updateRequest = new DepartmentUpdateRequest("New HR", null, null);
        DepartmentDto updatedDto = departmentService.update(1L, updateRequest);

        assertThat(updatedDto).isNotNull().extracting(DepartmentDto::name).isEqualTo("New HR");
    }

    @Test
    void updateDepartment_Success_UpdateDescriptionOnly() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        DepartmentUpdateRequest updateRequest = new DepartmentUpdateRequest(null, "New Description", null);

        DepartmentDto updatedDto = departmentService.update(1L, updateRequest);

        assertThat(updatedDto).isNotNull().extracting(DepartmentDto::description).isEqualTo("New Description");
    }

    @Test
    void updateDepartment_Success_UpdateEstablishedDateOnly() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        DepartmentUpdateRequest updateRequest = new DepartmentUpdateRequest(null, null, LocalDate.of(2025, 1, 1));

        DepartmentDto updatedDto = departmentService.update(1L, updateRequest);

        assertThat(updatedDto).isNotNull().extracting(DepartmentDto::establishedDate).isEqualTo(LocalDate.of(2025, 1, 1));
    }
}
