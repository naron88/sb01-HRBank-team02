package com.practice.hrbank.service;


import com.practice.hrbank.dto.DepartmentDTO;
import com.practice.hrbank.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;


    public List<DepartmentDTO> findAll() {
        return null;
    }


    public DepartmentDTO createDepartment() {
        return null;
    }


    public DepartmentDTO findById() {
        return null;
    }


    public void deleteDepartment() {

    }


    public DepartmentDTO updateDepartment() {
        return null;
    }
}
