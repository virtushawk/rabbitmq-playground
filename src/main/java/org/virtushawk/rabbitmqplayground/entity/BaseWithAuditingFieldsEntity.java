package org.virtushawk.rabbitmqplayground.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base Entity class contains additional auditing fields
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseWithAuditingFieldsEntity extends BaseEntity {

    @CreatedDate
    private long createdAt;

    @LastModifiedDate
    private long lastModifiedAt;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
