package org.virtushawk.rabbitmqplayground.entity.view;

import org.virtushawk.rabbitmqplayground.entity.BaseWithAuditingFieldsDTO;
import org.virtushawk.rabbitmqplayground.entity.OrderStatus;

/**
 * Represents user's order on UI
 */
public class OrderViewModel extends BaseWithAuditingFieldsDTO {

    private ReceiptViewModel receipt;

    private OrderStatus status;

    private String email;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ReceiptViewModel getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptViewModel receipt) {
        this.receipt = receipt;
    }
}
