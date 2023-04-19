package org.virtushawk.rabbitmqplayground.service;

import org.virtushawk.rabbitmqplayground.entity.Order;

/**
 * Service for {@link Order}
 */
public interface OrderService extends AbstractService<Order> {

    /**
     * Process order
     */
    void processOrder(Order order);
}
