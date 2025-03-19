package com.practice.hrbank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Instant createAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate establishedDate;

    public Department(String name, String description, LocalDate establishedDate) {
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
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

            // 이름, 설명, 설립일을 각각 하나씩 수정할 수 있도록 변경함
            // description만 수정하는 경우, name과 establishedDate는 기존 값을 유지
        }
    }

    public Department(Long id, String name, String description, LocalDate establishedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.establishedDate = establishedDate;
    }
}
