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
 * Listens to dead process order queue and process dead orders
 */
@Service
@RabbitListener(queues = JMSConfiguration.DEAD_PROCESS_ORDER_QUEUE, id = "process-order-dead-letter-listener")
public class ProcessOrderDeadLetterListener {

    public static final Logger LOGGER = LogManager.getLogger(ProcessOrderDeadLetterListener.class);

    private final OrderService orderService;

    public ProcessOrderDeadLetterListener(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Process dead order
     *
     * @param order the order
     */
    @RabbitHandler
    public void processOrder(Order order) {
        LOGGER.info("Dead order {} received", order.getUuid());

        order.setStatus(OrderStatus.FAILED);
        orderService.create(order);
    }
}
