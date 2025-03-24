package com.practice.hrbank.repository;

import com.practice.hrbank.entity.ChangeLog;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {

    @Query("SELECT c FROM ChangeLog c WHERE " +
        "(:employeeNumber IS NULL OR c.employeeNumber = :employeeNumber) AND " +
        "(:type IS NULL OR c.type = :type) AND " +
        "(:memo IS NULL OR c.memo LIKE CONCAT('%', :memo, '%')) AND " +
        "(:ipAddress IS NULL OR c.ipAddress = :ipAddress) AND " +
        "c.at >= COALESCE(:atFrom, '1970-01-01T00:00:00') AND " +
        "c.at <= COALESCE(:atTo, CURRENT_TIMESTAMP) AND " +
        "c.id > COALESCE(:idAfter, 0) " +
        "ORDER BY c.at DESC, c.id ASC")
    Page<ChangeLog> findByFilters(
        @Param("employeeNumber") String employeeNumber,
        @Param("type") String type,
        @Param("memo") String memo,
        @Param("ipAddress") String ipAddress,
        @Param("atFrom") Instant atFrom,
        @Param("atTo") Instant atTo,
        @Param("idAfter") Long idAfter,
        Pageable pageable
    );


    @Query("SELECT COUNT(c) FROM ChangeLog c WHERE c.at BETWEEN :fromDate AND :toDate")
    Long countByAtBetween(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);
}
