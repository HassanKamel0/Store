package com.example.demo.controllers;
import com.example.demo.DTO.request.LineItemRequest;
import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.response.LineItemResponse;
import com.example.demo.services.serviceImpl.LineItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/lineItem")
public class LineItemController {
    @Autowired
    private LineItemServiceImpl lineItemServiceImpl;
    @GetMapping("/{lineItemId}")
    public ResponseEntity<LineItemResponse> getLineItem(@PathVariable Long lineItemId){
        return new ResponseEntity<>(lineItemServiceImpl.getLineItem(lineItemId), HttpStatus.OK);
    }
    @PostMapping
    public void postLineItem(@RequestBody ProductLineItemRequest productLineItemRequest){
        lineItemServiceImpl.postLineItem(productLineItemRequest);
    }
    @DeleteMapping("/{lineItemId}")
    public void deleteLineItem(@PathVariable Long lineItemId){
        lineItemServiceImpl.deleteLineItem(lineItemId);
    }
    @PutMapping("/{lineItemId}")
    public void updateProduct(@PathVariable Long lineItemId,@RequestBody LineItemRequest lineItemRequest){
        lineItemServiceImpl.updateProduct(lineItemId,lineItemRequest);
    }
}
