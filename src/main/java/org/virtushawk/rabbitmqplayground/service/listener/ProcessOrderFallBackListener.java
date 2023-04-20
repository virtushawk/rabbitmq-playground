package org.virtushawk.rabbitmqplayground.service.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.virtushawk.rabbitmqplayground.config.JMSConfiguration;
import org.virtushawk.rabbitmqplayground.entity.Order;
import org.virtushawk.rabbitmqplayground.entity.OrderStatus;
import org.virtushawk.rabbitmqplayground.service.OrderService;

/**
 * Listens to fall back queue and process failed orders
 */
@Service
@RabbitListener(queues = JMSConfiguration.FALL_BACK_ORDER_QUEUE, id = "process-order-fall-back-listener")
public class ProcessOrderFallBackListener {

    public static final Logger LOGGER = LogManager.getLogger(ProcessOrderFallBackListener.class);

    private final OrderService orderService;

    public ProcessOrderFallBackListener(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Process failed order
     *
     * @param order the failed order
     */
    @RabbitHandler
    public void processOrder(Order order) {
        LOGGER.info("failed order {} received", order.getUuid());

        order.setStatus(OrderStatus.FAILED);
        orderService.create(order);
    }
}
