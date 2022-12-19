package com.example.demo.controllers;
import com.example.demo.DTO.request.LineItemRequest;
import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.response.LineItemResponse;
import com.example.demo.services.serviceImpl.LineItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/lineItem")
public class LineItemController {
    @Autowired
    private LineItemServiceImpl lineItemServiceImpl;
    @GetMapping("/{lineItemId}")
    public ResponseEntity<LineItemResponse> getLineItem(@PathVariable Long lineItemId){
        return new ResponseEntity<>(lineItemServiceImpl.getLineItem(lineItemId), HttpStatus.OK);}
    @PostMapping
    public ResponseEntity<Object> postLineItem(@RequestBody ProductLineItemRequest productLineItemRequest){
       URI location= lineItemServiceImpl.postLineItem(productLineItemRequest);
        return ResponseEntity.created(location).build();}
    @DeleteMapping("/{lineItemId}")
    public ResponseEntity deleteLineItem(@PathVariable Long lineItemId){
        lineItemServiceImpl.deleteLineItem(lineItemId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping("/{lineItemId}")
    public ResponseEntity updateLineItem(@PathVariable Long lineItemId, @RequestBody LineItemRequest lineItemRequest){
        lineItemServiceImpl.updateLineItem(lineItemId,lineItemRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
