package com.example.demo.controllers;
import com.example.demo.DTO.request.CartRequest;
import com.example.demo.DTO.response.CartResponse;
import com.example.demo.services.serviceImpl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/cart")
public class CartController {
    @Autowired
    private CartServiceImpl cartServiceImpl;
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long cartId){
     return new ResponseEntity<>(cartServiceImpl.getCart(cartId), HttpStatus.OK);
    }
    @PostMapping
    public void postCart(@RequestBody CartRequest cartRequest){
      cartServiceImpl.postCart(cartRequest);
    }
    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId){
       cartServiceImpl.deleteCart(cartId);
    }
    @PutMapping("/{cartId}")
    public void updateCart(@PathVariable Long cartId,@RequestBody CartRequest request){
      cartServiceImpl.updateCart(cartId,request);
    }
}
