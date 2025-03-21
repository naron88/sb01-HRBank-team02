package com.practice.hrbank.controller;

import com.practice.hrbank.dto.changeLog.ChangeLogRequestDto;
import com.practice.hrbank.dto.changeLog.CursorPageResponseChangeLogDto;
import com.practice.hrbank.dto.changeLog.DiffDto;
import com.practice.hrbank.service.ChangeLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/change-logs")
public class ChangeLogController {

    private final ChangeLogService changeLogsService;

    @Operation(summary = "Get 메서드 예제", description = "기본 전체 조회")
    @GetMapping()
    public ResponseEntity<CursorPageResponseChangeLogDto> search(
            @Parameter(description = "DTO", required = true) @ModelAttribute ChangeLogRequestDto changeLogRequestDto) {

        changeLogsService.getChangeLogs(changeLogRequestDto);

        return null;
    }

    @GetMapping("/{id}/diffs")
    public ResponseEntity<List<DiffDto>> diffs(@PathVariable("id") Long id) {
        List<DiffDto> diffs = changeLogsService.getDiffs(id);
        return ResponseEntity.ok(diffs);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate) {

        Long count = changeLogsService.getChangeLogCount(fromDate, toDate);
        return ResponseEntity.ok(count);
    }
}
