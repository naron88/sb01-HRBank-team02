package com.practice.hrbank.service;

import com.practice.hrbank.dto.DepartmentDto;
import com.practice.hrbank.repository.DepartmentRepository;
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


    public DepartmentDto create(DepartmentDto departmentDTO) {
        return null;
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
