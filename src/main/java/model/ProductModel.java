package model;

import utils.BigDecimalUtils;

import java.math.BigDecimal;

public class ProductModel {

    private String description;
    private BigDecimal price;
    private int quantity;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPrettyPrice() {
        return BigDecimalUtils.toPrettyString(price);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
