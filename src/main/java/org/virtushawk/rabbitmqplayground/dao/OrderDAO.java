package org.virtushawk.rabbitmqplayground.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.virtushawk.rabbitmqplayground.entity.Order;

/**
 * DAO for {@link Order}
 */
public interface OrderDAO extends JpaRepository<Order, String> {

}
