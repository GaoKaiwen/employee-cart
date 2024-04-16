package model;

import com.opencsv.bean.CsvDate;
import utils.BigDecimalUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Purchase {

    private String employee;
    private String description;
    private BigDecimal price;
    private int quantity;
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate date;

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

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
