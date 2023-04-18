package org.virtushawk.rabbitmqplayground.entity.view;

import org.virtushawk.rabbitmqplayground.entity.BaseDTO;

import java.math.BigDecimal;

/**
 * Represents sales item object on UI
 */
public class SalesItemViewModel extends BaseDTO {

    private String name;
    private String description;
    private String picture;
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
