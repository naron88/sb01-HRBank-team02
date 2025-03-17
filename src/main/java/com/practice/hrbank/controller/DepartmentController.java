package com.practice.hrbank.controller;

import com.practice.hrbank.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


}
