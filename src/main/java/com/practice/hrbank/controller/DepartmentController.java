package com.practice.hrbank.controller;

import com.practice.hrbank.dto.department.DepartmentDto;
import com.practice.hrbank.dto.department.DepartmentUpdateRequest;
import com.practice.hrbank.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) {
        DepartmentDto createdDepartment = departmentService.create(departmentDto);
        return ResponseEntity.created(URI.create("/api/departments/" + createdDepartment.id()))
                .body(createdDepartment);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentUpdateRequest departmentUpdateRequest
    ) {
        DepartmentDto updatedDepartment = departmentService.update(id, departmentUpdateRequest);
        return ResponseEntity.ok(updatedDepartment);
    }
}