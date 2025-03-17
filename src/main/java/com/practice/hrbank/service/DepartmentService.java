package com.practice.hrbank.service;

import com.practice.hrbank.dto.DepartmentDTO;
import com.practice.hrbank.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public List<DepartmentDTO> findAll() {
        return null;
    }


    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        return null;
    }


    public DepartmentDTO findById(Long id) {
        return null;
    }


    public void deleteDepartment(Long id) {

    }


    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        return null;
    }

}
