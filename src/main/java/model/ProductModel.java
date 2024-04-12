package model;

import utils.BigDecimalUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductModel {

    private String description;
    private BigDecimal price;
    private int quantity;
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
