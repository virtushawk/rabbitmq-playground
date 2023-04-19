package org.virtushawk.rabbitmqplayground.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Represents an item for sale
 */
@Entity
@Table(name = "SALES_ITEM")
public class SalesItem extends BaseEntity {

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "PRICE", nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "UUID", nullable = false, insertable = false, updatable = false)
    private Receipt receipt;

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

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
