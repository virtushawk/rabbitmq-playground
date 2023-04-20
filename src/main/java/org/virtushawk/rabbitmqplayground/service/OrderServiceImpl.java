package org.virtushawk.rabbitmqplayground.service;

import org.springframework.stereotype.Service;
import org.virtushawk.rabbitmqplayground.dao.OrderDAO;
import org.virtushawk.rabbitmqplayground.entity.Order;
import org.virtushawk.rabbitmqplayground.entity.OrderStatus;
import org.virtushawk.rabbitmqplayground.service.sender.OrderSender;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * Implementation of {@link OrderService}
 */
@Service
public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO;

    OrderSender orderSender;

    Random random;

    public OrderServiceImpl(OrderDAO orderDAO, OrderSender orderSender) throws NoSuchAlgorithmException {
        this.orderDAO = orderDAO;
        this.orderSender = orderSender;
        this.random = SecureRandom.getInstanceStrong();
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
    public void delete(Order entity) {
        orderDAO.delete(entity);
    }

    @Override
    public Order update(Order entity) {
        return orderDAO.save(entity);
    }

    @Override
    public void processOrder(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);

        if (random.nextBoolean()) {
            //simulate failed scenario
            order.setEmail(null);
        }

        orderSender.sendOrderToProcess(order);
    }

    @Override
    public void retryFailedOrder(Order order) {
        order.setStatus(OrderStatus.IN_PROGRESS);
        orderSender.sendFailedOrderToRetry(order);
    }

    @Override
    public List<Order> getFailedOrders() {
        return orderDAO.findAllByStatus(OrderStatus.FAILED);
    }

    @Override
    public void archiveOrder(Order order) {
        order.setStatus(OrderStatus.ARCHIVED);

        orderDAO.save(order);
    }
}
