package org.virtushawk.rabbitmqplayground.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.virtushawk.rabbitmqplayground.config.JMSConfiguration;
import org.virtushawk.rabbitmqplayground.dao.OrderDAO;
import org.virtushawk.rabbitmqplayground.entity.Order;

import java.util.List;

/**
 * Implementation of {@link OrderService}
 */
@Service
public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO;

    RabbitTemplate rabbitTemplate;

    public OrderServiceImpl(OrderDAO orderDAO, RabbitTemplate rabbitTemplate) {
        this.orderDAO = orderDAO;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public Order create(Order entity) {
        return orderDAO.save(entity);
    }

    @Override
    public void processOrder(Order order) {
        rabbitTemplate.convertAndSend(JMSConfiguration.SIMPLE_EXCHANGE,"simple-queue", order);
    }
}
