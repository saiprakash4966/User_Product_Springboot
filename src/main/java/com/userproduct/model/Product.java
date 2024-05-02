package com.userproduct.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.userproduct.exception.CustomExceptionHandler;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String productName;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate manufactureDate;

    @JsonFormat(pattern = "MM-dd-yyyy")
    private LocalDate expiryDate;

//    @DecimalMin(value = "0.0", message = "Unit price cannot be negative")
    private BigDecimal unitPrice;

    public Product()
    {

    }

    public Product(Long productId, BigDecimal unitPrice, LocalDate expiryDate, LocalDate manufactureDate, String productName) {
        this.productId = productId;
        this.unitPrice = unitPrice;
        this.expiryDate = expiryDate;
        this.manufactureDate = manufactureDate;
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }
    public void setId(Long productId) {
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        if (unitPrice != null && unitPrice.compareTo(BigDecimal.ZERO) >= 0) {
            this.unitPrice = unitPrice;
        } else {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", manufactureDate=" + manufactureDate +
                ", expiryDate=" + expiryDate +
                ", unitPrice=" + unitPrice +
                '}';
    }



}
