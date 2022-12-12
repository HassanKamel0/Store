package com.example.demo.controllers;
import java.net.URI;

import com.example.demo.DTO.request.CustomerRequest;
import com.example.demo.DTO.response.CustomerResponse;
import com.example.demo.services.serviceImpl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.example.demo.DemoApplication;
import com.example.demo.entity.Customer;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerControllerTest {
    @LocalServerPort
    int randomServerPort;
    @Mock
    CustomerServiceImpl customerServiceImpl;
    @Test
    void shouldGetCustomer() throws Exception {
        Customer customer=new Customer(100L,"Hassan","hassan@gmail.com","password");
        CustomerResponse response=new CustomerResponse();
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        when(customerServiceImpl.getCustomer(any())).thenReturn(response);
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/customer/customerId");
        ResponseEntity<CustomerResponse> responseCustomer = restTemplate.getForEntity(uri,CustomerResponse.class);
        assertThat(responseCustomer.getStatusCodeValue()).isEqualTo(201);
    }
    @Test
    void postCustomer() throws Exception{
        CustomerRequest request=new CustomerRequest();
        request.setName("Test");
        request.setEmail("test@gmail.com");
        request.setPassword("password");
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/customer");
        ResponseEntity<Customer> createdCustomer = restTemplate.postForEntity(uri,request, Customer.class);
        assertThat(createdCustomer.getStatusCodeValue()).isEqualTo(201);
        assertThat(createdCustomer.getHeaders().getLocation().getPath()).isEqualTo("/api/v1/customer/1");
    }
    @Test
    void deleteCustomer() throws Exception {
        // given
//        Customer customer=new Customer(100L,"Hassan","hassan@gmail.com","password");
//        mockMvc.perform(post("/api/v1/customer")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(customer)))
//                .andExpect(status().isOk());
//        MvcResult getStudentsResult = mockMvc.perform(get("/api/v1/customer")
//                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
//        String contentAsString = getStudentsResult.getResponse().getContentAsString();
//        List<Customer> customers = objectMapper.readValue(contentAsString,new TypeReference<>() {});
//        long id = customers.stream()
//                .filter(s -> s.getEmail().equals(customer.getEmail()))
//                .map(Customer::getId).findFirst()
//                .orElseThrow(() ->
//                        new IllegalStateException(
//                                "student with email: " + customer.getEmail() + " not found"));
//        // when
//        customerController.deleteCustomer(customer.getId());
//        ResultActions resultActions = mockMvc.perform(delete("/api/v1/customer/" + customer.getId()));
//        // then
//        resultActions.andExpect(status().isOk());
//        boolean exists = customerRepository.existsById(customer.getId());
//        assertThat(exists).isFalse();
    }
    @Test
    void updateCustomer() throws Exception{

//        Customer customer=new Customer(100L,"Hassn","hasan@gmail.com","password");
//        CustomerRequest request=new CustomerRequest();
//        request.setName("Hassan");
//        request.setEmail("hassan@gmail.com");
//
//        customerController.updateCustomer(customer.getId(),request);
//        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
//
//        ArgumentCaptor<Customer> argumentCaptor=ArgumentCaptor.forClass(Customer.class);
//        verify(customerRepository).save(argumentCaptor.capture());
//        Customer capturedCustomer=argumentCaptor.getValue();
//        assertEquals(request.getName(),capturedCustomer.getName());
//        assertEquals(request.getEmail(),capturedCustomer.getEmail());
       // verify(customerRepository,never()).save(any());
    }
}