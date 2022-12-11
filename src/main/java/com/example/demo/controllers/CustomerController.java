package com.example.demo.controllers;
import com.example.demo.DTO.Request.CustomerRequest;
import com.example.demo.DTO.Response.CustomerResponse;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Tables.Customer;
import com.example.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerService customerService,
                              CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }
    @GetMapping(path = "/{customerId}",consumes = "application/json", produces = "application/json")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long customerId){
        return new ResponseEntity<> (customerService.getCustomer(customerId), HttpStatus.OK);
    }
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> postCustomer(@RequestBody CustomerRequest request){
        URI location= customerService.postCustomer(request);
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId){
        customerService.deleteCustomer(customerId);
    }
    @PutMapping(path = "{customerId}")
    public void updateCustomer(@PathVariable Long customerId,@RequestBody CustomerRequest request){
        customerService.updateCustomer(customerId,request);
    }
}