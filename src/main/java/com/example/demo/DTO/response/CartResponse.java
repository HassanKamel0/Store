package com.example.demo.DTO.response;

import java.time.LocalDate;

public class CartResponse {
    private LocalDate createdDate;
    private int totalPrice;
    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
