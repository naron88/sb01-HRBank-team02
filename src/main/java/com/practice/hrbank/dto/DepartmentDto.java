package com.practice.hrbank.dto;


import java.time.LocalDate;


public record DepartmentDto(
        Long id,
        String name,
        String description,
        LocalDate establishedDate
) {
}
