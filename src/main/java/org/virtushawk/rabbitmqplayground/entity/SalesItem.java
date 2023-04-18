package org.virtushawk.rabbitmqplayground.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * Represents an item for sale
 */
@Entity
@Table(name = "SALES_ITEM")
public class SalesItem extends BaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Lob
    @Column(name = "DESCRIPTION")
    @Basic(fetch = FetchType.LAZY)
    private String description;

    @Lob
    @Column(name = "PICTURE")
    @Basic(fetch = FetchType.LAZY)
    private String picture;

    @Column(name = "PRICE", nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
