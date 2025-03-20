package com.practice.hrbank.controller;

import com.practice.hrbank.dto.department.DepartmentDto;
import com.practice.hrbank.dto.department.DepartmentUpdateRequest;
import com.practice.hrbank.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
        try {
            // 부서 삭제 전에 해당 부서에 소속된 직원이 있는지 확인
            boolean hasEmployees = departmentService.hasEmployees(id);
            if (hasEmployees) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null); // 소속된 직원이 있어 삭제할 수 없는 경우
            }

            // 부서 삭제
            boolean deleted = departmentService.deleteDepartment(id);
            if (deleted) {
                return ResponseEntity.noContent().build(); // 삭제 성공
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 부서가 존재하지 않는 경우
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 서버 오류
        }
    }


}