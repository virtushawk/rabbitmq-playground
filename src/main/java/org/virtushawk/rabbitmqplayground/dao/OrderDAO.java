package org.virtushawk.rabbitmqplayground.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.virtushawk.rabbitmqplayground.entity.Order;
import org.virtushawk.rabbitmqplayground.entity.OrderStatus;

import java.util.List;

/**
 * DAO for {@link Order}
 */
public interface OrderDAO extends JpaRepository<Order, String> {

    /**
     * Find all entities by specified status
     *
     * @param orderStatus {@link OrderStatus} status
     * @return list of entities with the same status
     */
    List<Order> findAllByStatus(OrderStatus orderStatus);
}
