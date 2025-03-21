package com.practice.hrbank.controller;

import com.practice.hrbank.dto.changeLog.ChangeLogDto;
import com.practice.hrbank.dto.changeLog.ChangeLogRequestDto;
import com.practice.hrbank.dto.changeLog.CursorPageResponseChangeLogDto;
import com.practice.hrbank.dto.changeLog.DiffDto;
import com.practice.hrbank.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/change-logs")
public class ChangeLogController {

    private final ChangeLogService changeLogsService;

    @GetMapping()
    public ResponseEntity<CursorPageResponseChangeLogDto> search(@ModelAttribute ChangeLogRequestDto changeLogRequestDto) {

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
