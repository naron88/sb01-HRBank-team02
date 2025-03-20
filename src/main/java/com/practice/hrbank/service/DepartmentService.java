package com.practice.hrbank.service;

import com.practice.hrbank.dto.DepartmentDto;
import com.practice.hrbank.entity.Department;
import com.practice.hrbank.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public List<DepartmentDto> findAll() {
        return null;
    }

    @Transactional
    public DepartmentDto create(DepartmentDto departmentDTO) {
        if (departmentRepository.existsByName(departmentDTO.name())) {
            throw new IllegalArgumentException("부서 중복");
        }

        Department department = new Department(
                departmentDTO.name(),
                departmentDTO.description(),
                departmentDTO.establishedDate()
        );

        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentDto(
                savedDepartment.getId(),
                savedDepartment.getName(),
                savedDepartment.getDescription(),
                savedDepartment.getEstablishedDate()
        );
    }


    public DepartmentDto findById(Long id) {
        return null;
    }


    public void delete(Long id) {

    }


    public DepartmentDto update(Long id, DepartmentDto departmentDTO) {
        return null;
    }

}
