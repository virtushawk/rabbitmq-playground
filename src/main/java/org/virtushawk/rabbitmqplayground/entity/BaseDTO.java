package org.virtushawk.rabbitmqplayground.entity;

/**
 * Base DTO class contains common fields for all DTOs
 */
public abstract class BaseDTO {
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
