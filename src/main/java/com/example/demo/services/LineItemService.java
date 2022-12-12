package com.example.demo.services;
import com.example.demo.DTO.request.LineItemRequest;
import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.response.LineItemResponse;

public interface LineItemService {
    LineItemResponse getLineItem(Long lineItemId);
    void postLineItem(ProductLineItemRequest request);
    void deleteLineItem(Long lineItemId);
    void updateProduct(Long lineItemId, LineItemRequest request);
    void putProductInLineItem(ProductLineItemRequest productLineItemRequest);
}
