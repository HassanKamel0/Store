package com.example.demo.services;

import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.request.ProductRequest;
import com.example.demo.DTO.response.ProductResponse;

public interface ProductService {
    ProductResponse getProduct(Long productId);
    void postProduct(ProductRequest productRequest);
    void deleteProduct(Long productId);
    void updateProduct(Long productId, ProductRequest request);
    void postProductInLineItem(ProductLineItemRequest lineItemRequest);
}
