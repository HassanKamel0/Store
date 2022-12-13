package com.example.demo.services.serviceImpl;
import com.example.demo.DTO.request.LineItemRequest;
import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.response.LineItemResponse;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.LineItemRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.entity.Cart;
import com.example.demo.entity.LineItem;
import com.example.demo.entity.Product;
import com.example.demo.services.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class LineItemServiceImpl implements LineItemService {
    @Autowired private LineItemRepository lineItemRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CartRepository cartRepository;
    public LineItemResponse getLineItem(Long lineItemId) {
        boolean exist = lineItemRepository.existsById(lineItemId);
        if (!exist)
            throw new IllegalStateException("Line Item with id " + lineItemId + " doesn't exist");
        LineItemResponse response = new LineItemResponse();
        LineItem lineItem = lineItemRepository.findById(lineItemId).get();
        response.setQuantity(lineItem.getQuantity());
        return response;
    }
    public URI postLineItem(ProductLineItemRequest request) {
        LineItem lineItem=new LineItem();
        lineItem.setProduct(productRepository.findById(request.getProductId()).get());
        lineItem.setCart(cartRepository.findById(request.getCartId()).get());
        lineItem.setQuantity(request.getQuantity());
        lineItemRepository.save(lineItem);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(lineItem.getId()).toUri();
        return location;
    }
    public void deleteLineItem(Long lineItemId) {
        boolean exist = lineItemRepository.existsById(lineItemId);
        if (!exist)
            throw new IllegalStateException("Line Item with id " + lineItemId + " doesn't exist");
        lineItemRepository.deleteById(lineItemId);
    }
    public void updateLineItem(Long lineItemId, LineItemRequest request) {
        LineItem lineItem = lineItemRepository.findById(lineItemId).orElseThrow(() -> new IllegalStateException(
                        "Line item with id " + lineItemId + " doesn't exist"));
        if (request.getQuantity() > 0)
            lineItem.setQuantity(request.getQuantity());
        lineItemRepository.save(lineItem);
    }
    public void putProductInLineItem(ProductLineItemRequest productLineItemRequest) {
        if (!productRepository.existsById(productLineItemRequest.getProductId()))
            throw new IllegalStateException("Product with id " + productLineItemRequest.getProductId() + " doesn't exist");
        LineItem lineItem = new LineItem();
        Product product = productRepository.findById(productLineItemRequest.getProductId()).get();
        lineItem.setProduct(product);
        lineItem.setQuantity(productLineItemRequest.getQuantity());
        Cart cart=  cartRepository.findById(productLineItemRequest.getCartId()).get();
        lineItem.setCart(cart);
        lineItemRepository.save(lineItem);
    }
}
