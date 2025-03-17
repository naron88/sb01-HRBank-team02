package com.practice.hrbank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;

@Entity
@Table(name = "backups")
@Getter
public class Backup {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String worker;

  @Column
  private Instant startedAt;

  @Column
  private Instant endedAt;

  @Column
  Status status;

  @OneToOne
  @JoinColumn(name = "metadata_id")
  Metadata file;

  public enum Status{
    IN_PROGRESS,
    COMPLETED,
    SKIPPED,
    FAILED
  }
}
