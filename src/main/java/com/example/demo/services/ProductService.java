package com.example.demo.services;

import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.request.ProductRequest;
import com.example.demo.DTO.response.ProductResponse;

import java.net.URI;

public interface ProductService {
    ProductResponse getProduct(Long productId);
    URI postProduct(ProductRequest productRequest);
    void deleteProduct(Long productId);
    void updateProduct(Long productId, ProductRequest request);
    URI postProductInLineItem(ProductLineItemRequest lineItemRequest);
}
