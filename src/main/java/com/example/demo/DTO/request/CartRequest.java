package com.example.demo.DTO.request;

import java.time.LocalDate;

public class CartRequest {
    private LocalDate createdDate;
    private int totalPrice;
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
