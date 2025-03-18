package com.practice.hrbank.controller;

import com.practice.hrbank.dto.changeLog.ChangeLogDto;
import com.practice.hrbank.dto.changeLog.CursorPageResponseChangeLogDto;
import com.practice.hrbank.dto.changeLog.DiffDto;
import com.practice.hrbank.service.ChangeLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/change-logs")
public class ChangeLogController {

    private final ChangeLogService changeLogsService;

    @GetMapping()
    public String search(ChangeLogDto changeLogDto) {
        return null;
    }

    @GetMapping("/{id}/diffs")
    public String diffs(@PathVariable Long id, DiffDto diffDto) {
        return null;
    }

    @GetMapping("/count")
    public String count(CursorPageResponseChangeLogDto changeLogDto) {
        return null;
    }
}
