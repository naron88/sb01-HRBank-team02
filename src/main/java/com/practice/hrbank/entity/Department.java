package com.practice.hrbank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "department")
@Getter
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate establishedDate;

    @Column(nullable = false)
    private int employeeCount;


    public Department(String name, String description, LocalDate establishedDate, int employeeCount) {
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
        this.employeeCount = employeeCount;

    }


    public void update(String name, String description, LocalDate establishedDate) {
        if (name != null) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
        if (establishedDate != null) {
            this.establishedDate = establishedDate;
        }
    }

}