package com.example.demo.services;

import com.example.demo.DTO.request.CartRequest;
import com.example.demo.DTO.response.CartResponse;
import com.example.demo.entity.Customer;

import java.net.URI;

public interface CartService {
    CartResponse getCart(Long cartId);
    URI postCart(CartRequest request);
    void deleteCart(Long cartId);
    void updateCart(Long cartId, CartRequest request);
    void createCartForCustomer(Customer customer);
}
