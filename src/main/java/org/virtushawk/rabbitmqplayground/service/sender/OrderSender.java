package org.virtushawk.rabbitmqplayground.service.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.virtushawk.rabbitmqplayground.config.JMSConfiguration;
import org.virtushawk.rabbitmqplayground.entity.Order;

/**
 * Service to send messages to broker
 */
@Service
public class OrderSender {
    RabbitTemplate rabbitTemplate;

    public OrderSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Send order to process
     *
     * @param order the order
     */
    public void sendOrderToProcess(Order order) {
        rabbitTemplate.convertAndSend(JMSConfiguration.SIMPLE_EXCHANGE,
                JMSConfiguration.PROCESS_ORDER_ROUTING_KEY, order);
    }

    /**
     * Send failed order to retry
     *
     * @param order the order
     */
    public void sendFailedOrderToRetry(Order order) {
        rabbitTemplate.convertAndSend(JMSConfiguration.SIMPLE_EXCHANGE,
                JMSConfiguration.RETRY_PROCESS_ORDER_ROUTING_KEY, order);
    }
}
