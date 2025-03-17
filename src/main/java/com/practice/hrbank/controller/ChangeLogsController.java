package com.practice.hrbank.controller;

import com.practice.hrbank.dto.changeLogs.ChangeLogDto;
import com.practice.hrbank.dto.changeLogs.CursorPageResponseChangeLogDto;
import com.practice.hrbank.dto.changeLogs.DiffDto;
import com.practice.hrbank.service.ChangeLogsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/change-logs")
public class ChangeLogsController {

    ChangeLogsService changeLogsService;

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
