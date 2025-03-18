package com.practice.hrbank.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "change-logs")
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 10)
    Type type;

    @Column(nullable = false, length = 30)
    String employeeNumber;
    @Column(nullable = false, length = 255)
    String detail;

    @Column(nullable = false, length = 255)
    String memo;

    @Column(nullable = false, length = 20)
    String ipAddress;

    @Column(nullable = false)
    Instant at;

    public enum Type{
        CREATED, UPDATED, DELETED
    }

}