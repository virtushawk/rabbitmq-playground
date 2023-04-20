package org.virtushawk.rabbitmqplayground.jms;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;

import java.util.Map;
import java.util.Optional;

/**
 * Message recoverer where message first is sent to specified exchange and after max attempts sent
 * to specified dead letter queue
 */
public class RepublishMessageMaxAttempts extends RepublishMessageRecoverer {

    private static final int ELLIPSIS_LENGTH = 3;

    public static final int DEFAULT_FRAME_MAX_HEADROOM = 20_000;

    public static final String X_RETRY_ATTEMPT = "x-retry-attempt";

    protected final Expression deadLetterExchangeName;

    protected final Expression deadLetterRoutingKey;

    private static final int MAX_EXCEPTION_MESSAGE_SIZE_IN_TRACE = 100 - ELLIPSIS_LENGTH;

    private volatile Integer maxStackTraceLength = -1;

    private static final int FRAME_MAX_HEADROOM = DEFAULT_FRAME_MAX_HEADROOM;

    private static final MessageDeliveryMode DELIVERY_MODE = MessageDeliveryMode.PERSISTENT;

    private final int maxRetry;


    public RepublishMessageMaxAttempts(AmqpTemplate errorTemplate, String errorExchange, String errorRoutingKey,
                                       String deadLetterExchange, String deadLetterRoutingKey, int maxRetry) {
        super(errorTemplate, new LiteralExpression(errorExchange), new LiteralExpression(errorRoutingKey));
        this.deadLetterExchangeName = new LiteralExpression(deadLetterExchange);
        this.deadLetterRoutingKey = new LiteralExpression(deadLetterRoutingKey);
        this.maxRetry = maxRetry;
    }

    @Override
    public void recover(Message message, Throwable cause) {
        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        String exceptionMessage = cause.getCause() != null ? cause.getCause().getMessage() : cause.getMessage();
        String[] processed = processStackTrace(cause, exceptionMessage);
        String stackTraceAsString = processed[0];
        String truncatedExceptionMessage = processed[1];
        if (truncatedExceptionMessage != null) {
            exceptionMessage = truncatedExceptionMessage;
        }

       Optional<Object> retryAttemptObject = Optional.ofNullable(headers.get(X_RETRY_ATTEMPT));

        int retryAttempt = 0;

        if (retryAttemptObject.isPresent()) {
            retryAttempt = (int) retryAttemptObject.get() + 1;
        }

        headers.put(X_EXCEPTION_STACKTRACE, stackTraceAsString);
        headers.put(X_EXCEPTION_MESSAGE, exceptionMessage);
        headers.put(X_RETRY_ATTEMPT, retryAttempt);
        headers.put(X_ORIGINAL_EXCHANGE, messageProperties.getReceivedExchange());
        headers.put(X_ORIGINAL_ROUTING_KEY, messageProperties.getReceivedRoutingKey());
        Map<? extends String, ?> additionalHeaders = additionalHeaders(message, cause);
        if (additionalHeaders != null) {
            headers.putAll(additionalHeaders);
        }

        if (messageProperties.getDeliveryMode() == null) {
            messageProperties.setDeliveryMode(RepublishMessageMaxAttempts.DELIVERY_MODE);
        }

        if (retryAttempt > maxRetry) {
            String exchangeName = this.deadLetterExchangeName.getValue(this.evaluationContext, message, String.class);
            String rk = this.deadLetterRoutingKey.getValue(this.evaluationContext, message, String.class);
            String routingKey = rk != null ? rk : this.prefixedOriginalRoutingKey(message);

            doSend(exchangeName, rk, message);
            if (this.logger.isWarnEnabled()) {
                this.logger.warn("Max retry attempts reached. Republishing failed message to specified dead letter queue '"
                        + exchangeName + "' with routing key " + routingKey);
            }

            return;
        }

        String exchangeName = this.errorExchangeNameExpression.getValue(this.evaluationContext, message, String.class);
        String rk = this.errorRoutingKeyExpression.getValue(this.evaluationContext, message, String.class);
        String routingKey = rk != null ? rk : this.prefixedOriginalRoutingKey(message);
        if (null != exchangeName) {
            doSend(exchangeName, routingKey, message);
            if (this.logger.isWarnEnabled()) {
                this.logger.warn("Republishing failed message to exchange '" + exchangeName
                        + "' with routing key " + routingKey);
            }
        }
        else {
            doSend(null, routingKey, message);
            if (this.logger.isWarnEnabled()) {
                this.logger.warn("Republishing failed message to the template's default exchange with routing key "
                        + routingKey);
            }
        }
    }

    private String[] processStackTrace(Throwable cause, String exceptionMessage) {
        String stackTraceAsString = getStackTraceAsString(cause);
        if (this.maxStackTraceLength < 0) {
            int maxStackTraceLen = RabbitUtils
                    .getMaxFrame(((RabbitTemplate) this.errorTemplate).getConnectionFactory());
            if (maxStackTraceLen > 0) {
                maxStackTraceLen -= FRAME_MAX_HEADROOM;
                this.maxStackTraceLength = maxStackTraceLen;
            }
        }
        return truncateIfNecessary(cause, exceptionMessage, stackTraceAsString);
    }

    private String[] truncateIfNecessary(Throwable cause, String exception, String stackTrace) {
        boolean truncated = false;
        String stackTraceAsString = stackTrace;
        String exceptionMessage = exception == null ? "" : exception;
        String truncatedExceptionMessage = exceptionMessage.length() <= MAX_EXCEPTION_MESSAGE_SIZE_IN_TRACE
                ? exceptionMessage
                : (exceptionMessage.substring(0, MAX_EXCEPTION_MESSAGE_SIZE_IN_TRACE) + "...");
        if (this.maxStackTraceLength > 0 &&
                stackTraceAsString.length() + exceptionMessage.length() > this.maxStackTraceLength) {

            if (!exceptionMessage.equals(truncatedExceptionMessage)) {
                int start = stackTraceAsString.indexOf(exceptionMessage);
                stackTraceAsString = stackTraceAsString.substring(0, start)
                        + truncatedExceptionMessage
                        + stackTraceAsString.substring(start + exceptionMessage.length());
            }
            int adjustedStackTraceLen = this.maxStackTraceLength - truncatedExceptionMessage.length();
            if (adjustedStackTraceLen > 0) {
                if (stackTraceAsString.length() > adjustedStackTraceLen) {
                    stackTraceAsString = stackTraceAsString.substring(0, adjustedStackTraceLen);
                    this.logger.warn("Stack trace in republished message header truncated due to frame_max "
                            + "limitations; "
                            + "consider increasing frame_max on the broker or reduce the stack trace depth", cause);
                    truncated = true;
                }
                else if (stackTraceAsString.length() + exceptionMessage.length() > this.maxStackTraceLength) {
                    this.logger.warn("Exception message in republished message header truncated due to frame_max "
                            + "limitations; consider increasing frame_max on the broker or reduce the exception "
                            + "message size", cause);
                    truncatedExceptionMessage = exceptionMessage.substring(0,
                            this.maxStackTraceLength - stackTraceAsString.length() - ELLIPSIS_LENGTH) + "...";
                    truncated = true;
                }
            }
        }
        return new String[] { stackTraceAsString, truncated ? truncatedExceptionMessage : null };
    }
}
