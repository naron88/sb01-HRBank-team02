package com.practice.hrbank.repository;

import com.practice.hrbank.entity.ChangeLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeLogsRepository extends JpaRepository<ChangeLogs, Long> {
}
