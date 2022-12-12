package com.example.demo.services;

import com.example.demo.DTO.request.CustomerRequest;
import com.example.demo.DTO.response.CustomerResponse;

import java.net.URI;

public interface CustomerService {
    CustomerResponse getCustomer(Long customerId);
    URI postCustomer(CustomerRequest request);
    void deleteCustomer(Long customerId);
    void updateCustomer(Long customerId, CustomerRequest request);

}
