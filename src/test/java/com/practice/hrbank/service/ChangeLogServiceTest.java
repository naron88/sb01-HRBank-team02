package com.practice.hrbank.service;

import com.practice.hrbank.repository.ChangeLogRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ChangeLogServiceTest {

    @Autowired
    ChangeLogRepository changeLogRepository;

    @Test
    public void 정렬테스트 () throws Exception {
        changeLogRepository.findAll(Sort.by(Sort.Direction.DESC, "at"));
    }

}