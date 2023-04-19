package org.virtushawk.rabbitmqplayground.entity;

/**
 * Base DTO class contains additional auditing fields
 */
public abstract class BaseWithAuditingFieldsDTO extends BaseDTO {

    private long createdAt;
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
