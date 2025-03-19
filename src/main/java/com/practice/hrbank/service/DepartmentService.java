package com.practice.hrbank.service;

import com.practice.hrbank.dto.DepartmentDto;
import com.practice.hrbank.entity.Department;
import com.practice.hrbank.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public DepartmentDto update(Long id, DepartmentDto departmentDto) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 부서가 존재하지 않습니다."));

        // System.out.println(">>> 기존 부서명: " + department.getName());
        // System.out.println(">>> 변경 요청 부서명: " + departmentDto.name()); 테스트용 코드

        // 이름이 변경되는 경우에만 중복 검사 수행 <<
        if (departmentDto.name() != null && !departmentDto.name().equals(department.getName())) {
            departmentRepository.findByName(departmentDto.name())
                    .filter(existingDepartment -> !existingDepartment.getId().equals(id))
                    .ifPresent(existing -> {
                        throw new IllegalArgumentException("이미 존재하는 부서명입니다.");
                    });
        }

        // null이 아닌 값만 업데이트 <<
        department.update(departmentDto.name(), departmentDto.description(), departmentDto.establishedDate());

        return new DepartmentDto(
                department.getId(),
                department.getName(),
                department.getDescription(),
                department.getEstablishedDate()
        );
    }
}
