package com.example.demo.services;
import com.example.demo.DTO.request.LineItemRequest;
import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.response.LineItemResponse;

import java.net.URI;

public interface LineItemService {
    LineItemResponse getLineItem(Long lineItemId);
    URI postLineItem(ProductLineItemRequest request);
    void deleteLineItem(Long lineItemId);
    void updateLineItem(Long lineItemId, LineItemRequest request);
    void putProductInLineItem(ProductLineItemRequest productLineItemRequest);
}
