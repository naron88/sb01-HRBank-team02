package com.practice.hrbank.service;

import com.practice.hrbank.dto.department.DepartmentDto;
import com.practice.hrbank.dto.department.DepartmentUpdateRequest;
import com.practice.hrbank.entity.Department;
import com.practice.hrbank.repository.DepartmentRepository;
import com.practice.hrbank.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

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
                departmentDTO.establishedDate(),
                0
        );

        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentDto(
                savedDepartment.getId(),
                savedDepartment.getName(),
                savedDepartment.getDescription(),
                savedDepartment.getEstablishedDate(),
                savedDepartment.getEmployeeCount()

        );
    }

    public DepartmentDto findById(Long id) {
        return null;
    }

    public void delete(Long id) {
    }

    public DepartmentDto update(Long id, DepartmentUpdateRequest departmentUpdateRequest) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

        // 이름이 변경되는 경우에만 중복 검사 수행
        if (departmentUpdateRequest.name() != null && !departmentUpdateRequest.name().equals(department.getName())) {
            departmentRepository.findByName(departmentUpdateRequest.name())
                    .filter(existingDepartment -> !existingDepartment.getId().equals(id))
                    .ifPresent(existing -> {
                        throw new IllegalArgumentException("이미 존재하는 부서명입니다.");
                    });
        }

        // null이 아닌 값만 업데이트
        department.update(departmentUpdateRequest.name(), departmentUpdateRequest.description(), departmentUpdateRequest.establishedDate());

        return new DepartmentDto(
                department.getId(),
                department.getName(),
                department.getDescription(),
                department.getEstablishedDate(),
                department.getEmployeeCount()

        );
    }

    public boolean hasEmployees(Long departmentId) {
        return employeeRepository.existsByDepartmentId(departmentId); // 부서 ID로 직원 존재 여부 확인
    }

    // 부서 삭제
    public boolean deleteDepartment(Long departmentId) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        if (departmentOptional.isPresent()) {
            departmentRepository.delete(departmentOptional.get()); // 부서 삭제
            return true;
        }
        return false; // 부서가 없으면 삭제할 수 없음
    }
}
