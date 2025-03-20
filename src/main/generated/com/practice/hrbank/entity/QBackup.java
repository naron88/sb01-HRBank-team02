package com.practice.hrbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBackup is a Querydsl query type for Backup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBackup extends EntityPathBase<Backup> {

    private static final long serialVersionUID = -1458990133L;

    public static final QBackup backup = new QBackup("backup");

    public final DateTimePath<java.time.Instant> endedAt = createDateTime("endedAt", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> startedAt = createDateTime("startedAt", java.time.Instant.class);

    public final EnumPath<Backup.Status> status = createEnum("status", Backup.Status.class);

    public final StringPath worker = createString("worker");

    public QBackup(String variable) {
        super(Backup.class, forVariable(variable));
    }

    public QBackup(Path<? extends Backup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBackup(PathMetadata metadata) {
        super(Backup.class, metadata);
    }

}

