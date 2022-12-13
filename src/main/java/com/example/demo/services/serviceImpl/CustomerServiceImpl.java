package com.example.demo.services.serviceImpl;
import com.example.demo.DTO.request.CustomerRequest;
import com.example.demo.DTO.response.CustomerResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.entity.Customer;
import com.example.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired private CustomerRepository customerRepository;
    @Autowired private CartServiceImpl cartServiceImpl;
    public CustomerResponse getCustomer(Long customerId) {
        boolean exist=customerRepository.existsById(customerId);
        if (!exist)
            throw new IllegalStateException("Customer with id "+customerId+" doesn't exist");
        CustomerResponse response = new CustomerResponse();
        Customer customer = customerRepository.findById(customerId).get();
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        return response;
    }
    public URI postCustomer(CustomerRequest request){
        Optional<Customer> customerOptional=customerRepository
                .findCustomerByEmail(request.getEmail());
        if(customerOptional.isPresent())
            throw new IllegalStateException("This email is taken");
        Customer customer = setCustomerFromRequest(request);
        cartServiceImpl.createCartForCustomer(customer);
        customerRepository.save(customer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(customer.getId()).toUri();
        return location;
    }

    private static Customer setCustomerFromRequest(CustomerRequest request) {
        Customer customer= new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(request.getPassword());
        return customer;
    }

    public void deleteCustomer(Long customerId) {
        boolean exist=customerRepository.existsById(customerId);
        if(!exist)
            throw new IllegalStateException("Customer with id "+customerId+" doesn't exist");
        customerRepository.deleteById(customerId);
    }
    public void updateCustomer(Long customerId, CustomerRequest request) {
        Customer customer=customerRepository.findById(customerId)
                .orElseThrow(()-> new IllegalStateException(
                        "Customer with id "+customerId+" doesn't exist"
                ));
        if(request.getName()!=null &&
                request.getName().length()>0 &&
                !Objects.equals(customer.getName(),request.getName())){
            customer.setName(request.getName());
        }
        if(request.getEmail()!=null &&
            request.getEmail().length()>0&&
                !Objects.equals(customer.getEmail(),request.getEmail())){
            customer.setEmail(request.getEmail());
        }
        if(request.getPassword()!=null && request.getPassword().length()>0){
            customer.setPassword(request.getPassword());
        }
        customerRepository.save(customer);
    }
}
