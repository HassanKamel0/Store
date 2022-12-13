package com.example.demo.controllers;
import java.net.URI;
import com.example.demo.DTO.request.CustomerRequest;
import com.example.demo.services.serviceImpl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.example.demo.DemoApplication;
import com.example.demo.entity.Customer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
    @MockBean CustomerServiceImpl customerService;
    @LocalServerPort
    int randomServerPort;
    @Test
    void shouldGetCustomer() throws Exception {
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/customer/1");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Customer> responseCustomer = restTemplate.getForEntity(uri,Customer.class);
        assertEquals(HttpStatus.OK,responseCustomer.getStatusCode());
        assertEquals(200,responseCustomer.getStatusCodeValue());
    }
    @Test
    void shouldPostCustomer() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/customer");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Customer> createdCustomer = restTemplate.postForEntity(uri,setCustomerRequest(), Customer.class);
        assertThat(createdCustomer.getStatusCodeValue()).isEqualTo(201);
//        assertThat(createdCustomer.getHeaders().getLocation().getPath()).isEqualTo("/api/v1/customer/1");
    }
    @Test
    void deleteCustomer() throws Exception {
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/customer/1");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.delete(uri);
    }
    @Test
    void updateCustomer() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/customer/1");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<CustomerRequest>(setCustomerRequest(), new HttpHeaders()), Void.class);
    }
    private static CustomerRequest setCustomerRequest() {
        CustomerRequest request=new CustomerRequest();
        request.setName("Test");
        request.setEmail("test@gmail.com");
        request.setPassword("password");
        return request;
    }
}