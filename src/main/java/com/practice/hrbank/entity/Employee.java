package com.practice.hrbank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "employees")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column(nullable = false)
  private Instant createAt;

  @LastModifiedDate
  private Instant updatedAt;

  @Column(nullable = false, length = 20)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String employeeNumber;

  @Column(nullable = false)
  private String position;

  @Column(nullable = false)
  private LocalDate hireDate;

  private Status status;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "metadata_id")
  private Metadata profileImage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id", nullable = false)
  private Department department;

  public enum Status {
    ACTIVE,
    ON_LEAVE,
    RESIGNED;
  }

  public Employee(String name, String email, String employeeNumber, String position,
      LocalDate hireDate, Metadata profileImage, Department department) {
    this.name = name;
    this.email = email;
    this.employeeNumber = employeeNumber;
    this.position = position;
    this.hireDate = hireDate;

    this.profileImage = profileImage;
    this.department = department;
  }
}
