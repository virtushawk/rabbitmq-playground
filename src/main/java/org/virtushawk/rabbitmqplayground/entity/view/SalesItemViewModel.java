package org.virtushawk.rabbitmqplayground.entity.view;

import org.virtushawk.rabbitmqplayground.entity.BaseDTO;

import java.math.BigDecimal;


/**
 * Represents sales item on UI
 */
public class SalesItemViewModel extends BaseDTO {

    private String title;
    private BigDecimal price;

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
}
