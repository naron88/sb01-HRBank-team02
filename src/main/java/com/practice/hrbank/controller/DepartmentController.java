package com.practice.hrbank.controller;

import com.practice.hrbank.dto.department.DepartmentDto;
import com.practice.hrbank.dto.department.DepartmentUpdateRequest;
import com.practice.hrbank.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        boolean deleted = departmentService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable Long id) {
        return departmentService.findById(id)
                .map(ResponseEntity::ok)  // 조회 성공 (200 OK)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // 부서 없음 (404 Not Found)
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getDepartments(
            @RequestParam(required = false) String nameOrDescription,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "0") int pageSize) {

        List<DepartmentDto> departmentDtos = departmentService.getDepartments(nameOrDescription, sortBy, null, pageSize);
        return ResponseEntity.ok(departmentDtos);
    }

}