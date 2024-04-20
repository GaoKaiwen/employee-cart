package model;

import com.opencsv.bean.CsvDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Purchase {

    private String employee;
    private String description;
    private BigDecimal price;
    private int quantity;
    @CsvDate(value = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTime;
    private boolean isDeleted;
    private String deletionJustification;

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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getDateFormatted() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getDeletionJustification() {
        return deletionJustification;
    }

    public void setDeletionJustification(String deletionJustification) {
        this.deletionJustification = deletionJustification;
    }
}
