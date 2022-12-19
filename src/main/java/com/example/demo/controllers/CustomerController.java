package com.example.demo.controllers;
import com.example.demo.DTO.request.CustomerRequest;
import com.example.demo.DTO.response.CustomerResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.services.serviceImpl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    @Autowired private CustomerServiceImpl customerServiceImpl;
    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("customerId") Long customerId){
        return new ResponseEntity<> (customerServiceImpl.getCustomer(customerId), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Object> postCustomer(@RequestBody CustomerRequest request){
        URI location= customerServiceImpl.postCustomer(request);
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(path = "{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable("customerId") Long customerId){
        customerServiceImpl.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PutMapping(path = "{customerId}")
    public ResponseEntity updateCustomer(@PathVariable Long customerId,@RequestBody CustomerRequest request){
        customerServiceImpl.updateCustomer(customerId,request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}