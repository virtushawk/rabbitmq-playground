package org.virtushawk.rabbitmqplayground.service.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.virtushawk.rabbitmqplayground.config.JMSConfiguration;
import org.virtushawk.rabbitmqplayground.entity.Order;
import org.virtushawk.rabbitmqplayground.entity.OrderStatus;
import org.virtushawk.rabbitmqplayground.entity.exception.EmailNotProvidedException;
import org.virtushawk.rabbitmqplayground.service.OrderService;

/**
 * Listens to process order queues
 */
@Service
@RabbitListener(queues = {JMSConfiguration.PROCESS_ORDER_QUEUE, JMSConfiguration.RETRY_ORDER_QUEUE},
        id = "process-order-listener")
public class ProcessOrderListener {

    public static final Logger LOGGER = LogManager.getLogger(ProcessOrderListener.class);

    private final OrderService orderService;
    public ProcessOrderListener(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Process order.
     *
     * @param order the order
     */
    @RabbitHandler
    public void processOrder(Order order) {
        String email = order.getEmail();

        if (email == null) {
            LOGGER.error("Order {} is sent without email", order.getUuid());
            throw new EmailNotProvidedException();
        }

        LOGGER.info("Received order {}", order.getUuid());

        order.setStatus(OrderStatus.DONE);
        orderService.create(order);
    }
}
