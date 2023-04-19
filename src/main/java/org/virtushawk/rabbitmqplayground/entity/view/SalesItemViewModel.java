package org.virtushawk.rabbitmqplayground.entity.view;

import org.virtushawk.rabbitmqplayground.entity.BaseDTO;

import java.math.BigDecimal;


/**
 * Represents sales item on UI
 */
public class SalesItemViewModel extends BaseDTO {

    private String title;

    private BigDecimal price;

    private ReceiptViewModel receipt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ReceiptViewModel getReceipt() {
        return receipt;
    }

    public void setReceipt(ReceiptViewModel receipt) {
        this.receipt = receipt;
    }
}
