package com.practice.hrbank.dto.changeLogs;

public record DiffDto(
        String propertyName,
        String before,
        String after) {

}
