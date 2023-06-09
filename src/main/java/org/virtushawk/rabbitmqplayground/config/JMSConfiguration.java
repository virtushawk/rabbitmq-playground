package org.virtushawk.rabbitmqplayground.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.virtushawk.rabbitmqplayground.jms.RepublishMessageMaxAttempts;

@Configuration
public class JMSConfiguration {

    public static final String SIMPLE_EXCHANGE = "simple-exchange";

    public static final String FAILED_LETTER_EXCHANGE = "failed-letter-exchange";

    public static final String WAIT_LETTER_EXCHANGE = "wait-letter-exchange";

    public static final String DEAD_LETTER_EXCHANGE = "dead-letter-exchange";

    public static final String PROCESS_ORDER_QUEUE = "q.process-order";

    public static final String RETRY_ORDER_QUEUE = "q.retry-process-order";

    public static final String FALL_BACK_ORDER_QUEUE = "q.fall-back-process-order";

    public static final String WAIT_PROCESS_ORDER_QUEUE = "q.wait-process-order";

    public static final String DEAD_PROCESS_ORDER_QUEUE = "q.dead-process-order-queue";

    public static final String PROCESS_ORDER_ROUTING_KEY = "process.order";

    public static final String FALL_BACK_ROUTING_KEY = "fall-back";

    public static final String RETRY_PROCESS_ORDER_ROUTING_KEY = "retry.process.order";

    public static final String WAIT_PROCESS_ORDER_ROUTING_KEY = "wait.process.order";

    public static final String DEAD_PROCESS_ORDER_ROUTING_KEY = "dead.process.order";

    public static final int DEFAULT_TTL = 10000;

    public static final int DEFAULT_MAX_LENGTH = 2;

    private final CachingConnectionFactory cachingConnectionFactory;

    public JMSConfiguration(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(PROCESS_ORDER_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(DEAD_PROCESS_ORDER_ROUTING_KEY)
                .ttl(DEFAULT_TTL)
                .maxLength(DEFAULT_MAX_LENGTH)
                .build();
    }

    @Bean
    Queue retryQueue() {
        return QueueBuilder.durable(RETRY_ORDER_QUEUE)
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(DEAD_PROCESS_ORDER_ROUTING_KEY)
                .ttl(DEFAULT_TTL)
                .maxLength(DEFAULT_MAX_LENGTH)
                .build();
    }

    @Bean
    Queue fallBackQueue() {
        return new Queue(FALL_BACK_ORDER_QUEUE, true);
    }

    @Bean
    Queue waitQueue() {
        return QueueBuilder.durable(WAIT_PROCESS_ORDER_QUEUE)
                .deadLetterExchange(SIMPLE_EXCHANGE)
                .deadLetterRoutingKey(PROCESS_ORDER_ROUTING_KEY)
                .ttl(1000)
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return new Queue(DEAD_PROCESS_ORDER_QUEUE, true);
    }

    @Bean
    Binding simpleQueueBinding() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with(PROCESS_ORDER_ROUTING_KEY);
    }

    @Bean
    Binding deadLetterQueueBinding() {
        return BindingBuilder.bind(fallBackQueue()).to(directExchange()).with(FALL_BACK_ROUTING_KEY);
    }

    @Bean
    Binding retryQueueBinding() {
        return BindingBuilder.bind(retryQueue()).to(topicExchange()).with(RETRY_PROCESS_ORDER_ROUTING_KEY);
    }

    @Bean
    Binding waitQueueBinding() {
        return BindingBuilder.bind(waitQueue()).to(waitExchange()).with(WAIT_PROCESS_ORDER_ROUTING_KEY);
    }

    @Bean
    Binding deadQueueBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_PROCESS_ORDER_ROUTING_KEY);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(SIMPLE_EXCHANGE);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(FAILED_LETTER_EXCHANGE);
    }

    @Bean
    public DirectExchange waitExchange() {
        return new DirectExchange(WAIT_LETTER_EXCHANGE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(jacksonJmsMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RetryOperationsInterceptor retryInterceptor(){
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(1)
                .recoverer(new RepublishMessageMaxAttempts(rabbitTemplate(), WAIT_LETTER_EXCHANGE,
                        WAIT_PROCESS_ORDER_ROUTING_KEY, FAILED_LETTER_EXCHANGE, FALL_BACK_ROUTING_KEY, 2))
                .build();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cachingConnectionFactory);
        factory.setMessageConverter(jacksonJmsMessageConverter());
        factory.setAdviceChain(retryInterceptor());
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonJmsMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
