package com.practice.hrbank.controller;

import com.practice.hrbank.dto.changeLog.ChangeLogDto;
import com.practice.hrbank.dto.changeLog.CursorPageResponseChangeLogDto;
import com.practice.hrbank.dto.changeLog.DiffDto;
import com.practice.hrbank.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/change-logs")
public class ChangeLogController {

    private final ChangeLogService changeLogsService;

    @GetMapping()
    public ResponseEntity<CursorPageResponseChangeLogDto> search(
            @RequestParam(required = false) String employeeNumber,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String memo,
            @RequestParam(required = false) String ipAddress,
            @RequestParam(required = false) String atFrom,
            @RequestParam(required = false) String atTo,
            @RequestParam(required = false) String idAfter,
            @RequestParam(required = false) String cursor,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "at") String sortField,
            @RequestParam(required = false, defaultValue = "desc") String sortDirection
    ) {
        CursorPageResponseChangeLogDto response = changeLogsService.search(
                employeeNumber, type, memo, ipAddress, atFrom, atTo, idAfter, cursor, size, sortField, sortDirection
        );


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}/diffs")
    public ResponseEntity<DiffDto> diffs(@PathVariable Long id, DiffDto diffDto) {
        return null;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(CursorPageResponseChangeLogDto changeLogDto) {
        return null;
    }
}
