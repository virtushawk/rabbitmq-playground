package org.virtushawk.rabbitmqplayground.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

@Configuration
public class JMSConfiguration {

    public static final String SIMPLE_EXCHANGE = "simple-exchange";

    public static final String DEAD_LETTER_EXCHANGE = "dead-letter-exchange";

    public static final String PROCESS_ORDER_QUEUE = "q.process-order";

    public static final String RETRY_ORDER_QUEUE = "q.retry-process-order";

    public static final String FALL_BACK_ORDER_QUEUE = "q.fall-back-process-order";

    public static final String PROCESS_ORDER_ROUTING_KEY = "process.order";

    public static final String FALL_BACK_ROUTING_KEY = "fall-back";

    public static final String RETRY_PROCESS_ORDER_ROUTING_KEY = "retry.process.order";

    private final CachingConnectionFactory cachingConnectionFactory;

    public JMSConfiguration(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    @Bean
    Queue queue() {
        return new Queue(PROCESS_ORDER_QUEUE, true);
    }

    @Bean
    Queue retryQueue() {
        return new Queue(RETRY_ORDER_QUEUE, true);
    }

    @Bean
    Queue fallBackQueue() {
        return new Queue(FALL_BACK_ORDER_QUEUE, true);
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
    public TopicExchange topicExchange() {
        return new TopicExchange(SIMPLE_EXCHANGE);
    }

    @Bean
    public DirectExchange directExchange() {
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
        return RetryInterceptorBuilder.stateless().maxAttempts(3)
                .backOffOptions(2000, 2.0, 100000)
                .recoverer(new RepublishMessageRecoverer(rabbitTemplate(), DEAD_LETTER_EXCHANGE,
                        FALL_BACK_ROUTING_KEY))
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
