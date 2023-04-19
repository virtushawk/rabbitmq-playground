package org.virtushawk.rabbitmqplayground.entity.view;

import org.virtushawk.rabbitmqplayground.entity.BaseDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 *  Represents receipt on UI
 */
public class ReceiptViewModel extends BaseDTO {

    private List<SalesItemViewModel> salesItems;

    private BigDecimal totalPrice;

    public List<SalesItemViewModel> getSalesItems() {
        return salesItems;
    }

    public void setSalesItems(List<SalesItemViewModel> salesItems) {
        this.salesItems = salesItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
