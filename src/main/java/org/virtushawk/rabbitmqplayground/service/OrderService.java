package org.virtushawk.rabbitmqplayground.service;

import org.virtushawk.rabbitmqplayground.entity.Order;

import java.util.List;

/**
 * Service for {@link Order}
 */
public interface OrderService extends AbstractService<Order> {

    /**
     * Process order
     */
    void processOrder(Order order);

    /**
     * Retry failed order
     *
     * @param order the order
     */
    void retryFailedOrder(Order order);

    /**
     * get all orders with OrderStatus.FAILED
     *
     * @return the failed orders
     */
    List<Order> getFailedOrders();

    /**
     * Change status to OrderStatus.ARCHIVED and store order
     *
     * @param order the order
     */
    void archiveOrder(Order order);
}
