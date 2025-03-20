package com.practice.hrbank.repository;

import com.practice.hrbank.entity.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {


}
