package org.virtushawk.rabbitmqplayground.entity;

/**
 * Contains {@link Order} states
 */
public enum OrderStatus {
    /**
     * Order is created
     */
    NEW,

    /**
     * Order is being processed
     */
    IN_PROGRESS,

    /**
     * Order was unsuccessful
     */
    FAILED,

    /**
     * Order is archived
     */
    ARCHIVED,

    /**
     * Order is finished
     */
    DONE
}
