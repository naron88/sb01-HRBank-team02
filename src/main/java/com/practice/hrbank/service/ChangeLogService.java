package com.practice.hrbank.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.hrbank.dto.changeLog.ChangeLogCreateRequest;
import com.practice.hrbank.dto.changeLog.DiffDto;
import com.practice.hrbank.dto.employee.EmployeeDto;
import com.practice.hrbank.entity.ChangeLog;
import com.practice.hrbank.repository.ChangeLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeLogService {

    private final ChangeLogRepository changeLogRepository;
    private final ObjectMapper objectMapper;

    public void save(ChangeLogCreateRequest departmentCreateRequest) {
        List<DiffDto> detail = getdiff(departmentCreateRequest.beforeEmployeeDto(), departmentCreateRequest.afterEmployeeDto());

        try {
            String detailJson = objectMapper.writeValueAsString(detail);

            switch(departmentCreateRequest.changeType()) {
                case CREATED:
                    changeLogRepository.save(new ChangeLog(
                        departmentCreateRequest.changeType(),
                        departmentCreateRequest.afterEmployeeDto().employeeNumber(),
                        detailJson,
                        departmentCreateRequest.memo(),
                        departmentCreateRequest.ipAddress()));
                    break;
                default:
                    changeLogRepository.save(new ChangeLog(
                            departmentCreateRequest.changeType(),
                            departmentCreateRequest.beforeEmployeeDto().employeeNumber(),
                            detailJson,
                            departmentCreateRequest.memo(),
                            departmentCreateRequest.ipAddress()
                    ));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<DiffDto> getdiff(EmployeeDto beforeEmployeeDto, EmployeeDto afterEmployeeDto) {

        List<DiffDto> diffs = new ArrayList<>();

        // 신규 등록
        if (beforeEmployeeDto == null) {
            diffs.add(new DiffDto("입사일", null, String.valueOf(afterEmployeeDto.hireData())));
            diffs.add(new DiffDto("이름", null, afterEmployeeDto.name()));
            diffs.add(new DiffDto("직함", null, afterEmployeeDto.position()));
            diffs.add(new DiffDto("부서명", null, afterEmployeeDto.departmentName()));
            diffs.add(new DiffDto("이메일", null, afterEmployeeDto.email()));
            diffs.add(new DiffDto("사번", null, afterEmployeeDto.employeeNumber()));
            diffs.add(new DiffDto("상태", null, String.valueOf(afterEmployeeDto.status())));
            return diffs;
        }
        // 삭제
        if (afterEmployeeDto == null) {
            diffs.add(new DiffDto("입사일", String.valueOf(beforeEmployeeDto.hireData()), null));
            diffs.add(new DiffDto("이름", beforeEmployeeDto.name(), null));
            diffs.add(new DiffDto("직함", beforeEmployeeDto.position(), null));
            diffs.add(new DiffDto("부서명", beforeEmployeeDto.departmentName(), null));
            diffs.add(new DiffDto("이메일", beforeEmployeeDto.email(), null));
            diffs.add(new DiffDto("상태", String.valueOf(afterEmployeeDto.status()), null));
            return diffs;
        }

        // 수정
        if(afterEmployeeDto.name()!=null && !beforeEmployeeDto.name().equals(afterEmployeeDto.name())) {
            diffs.add(new DiffDto("이름", beforeEmployeeDto.name(), afterEmployeeDto.name()));
        }
        if(afterEmployeeDto.email()!= null && !beforeEmployeeDto.email().equals(afterEmployeeDto.email())) {
            diffs.add(new DiffDto("이메일", beforeEmployeeDto.email(), afterEmployeeDto.email()));
        }
        if(afterEmployeeDto.departmentName() != null && !beforeEmployeeDto.departmentName().equals(afterEmployeeDto.departmentName())) {
            diffs.add(new DiffDto("부서", beforeEmployeeDto.departmentName(), afterEmployeeDto.departmentName()));
        }
        if(afterEmployeeDto.position() != null && !beforeEmployeeDto.position().equals(afterEmployeeDto.position())) {
            diffs.add(new DiffDto("직함", beforeEmployeeDto.position(), afterEmployeeDto.position()));
        }
        if(afterEmployeeDto.hireData() != null && !beforeEmployeeDto.hireData().equals(afterEmployeeDto.hireData())) {
            diffs.add(new DiffDto("입사일", String.valueOf(beforeEmployeeDto.hireData()), String.valueOf(afterEmployeeDto.hireData())));
        }
        if (afterEmployeeDto.status() != null && !beforeEmployeeDto.status().equals(afterEmployeeDto.status())) {
            diffs.add(new DiffDto("상태", String.valueOf(beforeEmployeeDto.status()), String.valueOf(afterEmployeeDto.status())));
        }
        return diffs;
    }
}
