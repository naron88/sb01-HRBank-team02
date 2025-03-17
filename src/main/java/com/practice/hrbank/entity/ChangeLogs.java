package com.practice.hrbank.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "change-logs")
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Type type;

    @Column(nullable = false)
    String employeeNumber;
    @Column(nullable = false)
    String detail;

    @Column(nullable = false)
    String memo;

    @Column(nullable = false)
    String ipAddress;

    @Column(nullable = false)
    Instant at;

    public enum Type{
        CREATED, UPDATED, DELETED
    }

}




















