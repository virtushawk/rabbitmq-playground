package org.virtushawk.rabbitmqplayground.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents receipt object that contains one or more sales items
 */
@Entity
@Table(name = "RECEIPT")
public class Receipt extends BaseEntity {

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    private List<SalesItem> salesItems;

    @Column(name = "TOTAL_PRICE", nullable = false, precision = 19, scale = 4)
    private BigDecimal totalPrice;

    public List<SalesItem> getSalesItems() {
        return salesItems;
    }

    public void setSalesItems(List<SalesItem> salesItems) {
        this.salesItems = salesItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
