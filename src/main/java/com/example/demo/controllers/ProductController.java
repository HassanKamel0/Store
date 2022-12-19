package com.example.demo.controllers;
import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.request.ProductRequest;
import com.example.demo.DTO.response.ProductResponse;
import com.example.demo.services.serviceImpl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productServiceImpl;
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId){
     return new ResponseEntity<>(productServiceImpl.getProduct(productId), HttpStatus.OK);}
    @PostMapping
    public ResponseEntity<Object> postProduct(@RequestBody ProductRequest productRequest){
     URI location= productServiceImpl.postProduct(productRequest);
     return ResponseEntity.created(location).build();
    }
    @PostMapping("/{lineItemId}")
    public ResponseEntity postProductInLineItem(@RequestBody ProductLineItemRequest LineItemRequest){
        URI location= productServiceImpl.postProductInLineItem(LineItemRequest);
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId){
        productServiceImpl.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId,@RequestBody ProductRequest request){
        productServiceImpl.updateProduct(productId,request);}
}
