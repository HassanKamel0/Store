package com.example.demo.services;
import com.example.demo.DTO.request.CustomerRequest;
import com.example.demo.services.serviceImpl.CartServiceImpl;
import com.example.demo.services.serviceImpl.CustomerServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import com.example.demo.DTO.response.CustomerResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.entity.Customer;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @InjectMocks private CustomerServiceImpl underTest;
    @Mock private CustomerRepository customerRepository;
    @Mock private CartServiceImpl cartServiceImpl;
    @Mock private CustomerResponse customerResponse;
    @Mock private CustomerRequest customerRequest;
    @Nested
    class whenGet {
        @Test
        void getCustomer() {
            //given
            Customer customer = new Customer(100L, "Hassan", "hassan@gmail.com", "password");
            CustomerResponse response = new CustomerResponse();
            response.setName(customer.getName());
            response.setEmail(customer.getEmail());

            given(customerRepository.existsById(customer.getId())).willReturn(true);
            when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
            //when
            var testResponse = underTest.getCustomer(customer.getId());
            //then
            assertEquals(testResponse.getEmail(), response.getEmail());
            assertEquals(testResponse.getName(), response.getName());
        }

        @Test
        void willThrowWhenCustomerDoesNotFoundWhenGet() {
            Long customerId = 10L;
            given(customerRepository.existsById(any())).willReturn(false);
            assertThatThrownBy(() -> underTest.getCustomer(customerId))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Customer with id " + customerId + " doesn't exist");
            verify(customerRepository, never()).findById(customerId);
        }
    }
    @Nested
    class whenPost {
        @Test
        void postCustomer() {
            //given
            Customer customer = new Customer();
            CustomerRequest customerRequest = new CustomerRequest();
            customerRequest.setName("Hassan");
            customerRequest.setEmail("hassan@gmail.com");
            customerRequest.setPassword("password");
            customer.setName(customerRequest.getName());
            customer.setEmail(customerRequest.getEmail());
            customer.setPassword(customerRequest.getPassword());
            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(customer.getId()).toUri();
            //when
            when(customerRepository.findCustomerByEmail(any())).thenReturn(Optional.empty());
            when(underTest.postCustomer(customerRequest)).thenReturn(location);
            //then
            ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
            verify(customerRepository).save(customerArgumentCaptor.capture());
            Customer capturedCustomer = customerArgumentCaptor.getValue();
            assertEquals(capturedCustomer.getName(), customer.getName());
            assertEquals(capturedCustomer.getEmail(), customer.getEmail());
            assertEquals(capturedCustomer.getPassword(), customer.getPassword());
        }
        @Test
        void willThrowWhenCustomerFoundWhenPost() {
           when(customerRepository.findCustomerByEmail(any())).thenReturn(Optional.of(new Customer()));
            assertThatThrownBy(() -> underTest.postCustomer(new CustomerRequest()))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("This email is taken");
            verify(customerRepository,never()).save(any());
        }
    }
    @Nested
    class whenDelete {
        @Test
        void deleteCustomer() {
            //given
            Customer customer = new Customer(100L, "Hassan", "hassan@gmail.com", "password");
            given(customerRepository.existsById(customer.getId())).willReturn(true);
            //when
            underTest.deleteCustomer(customer.getId());
            //then
            verify(customerRepository, times(1)).deleteById(customer.getId());
        }
        @Test
        void willThrowWhenCustomerDoesNotFoundWhenDelete() {
            Long customerId = 10L;
            given(customerRepository.existsById(customerId)).willReturn(false);
            assertThatThrownBy(() -> underTest.deleteCustomer(customerId))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Customer with id " + customerId + " doesn't exist");
            verify(customerRepository, never()).deleteById(customerId);
        }
    }
    @Nested
    class whenUpdate {
        @Test
        void updateCustomer() {
            //given
            Customer customer = new Customer(100L, "Hasan", "hasan@gmail.cm", "paword");
            CustomerRequest request = new CustomerRequest();
            request.setName("Hassan");
            request.setEmail("hassan@gmail.com");
            request.setPassword("password");
            when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
            //when
            underTest.updateCustomer(customer.getId(), request);
            //then
            if (request.getName() != null &&
                    request.getName().length() > 0 &&
                    !Objects.equals(customer.getName(), request.getName())) {
                customer.setName(request.getName());
            }
            if (request.getEmail() != null &&
                    request.getEmail().length() > 0 &&
                    !Objects.equals(customer.getEmail(), request.getEmail())) {
                customer.setEmail(request.getEmail());
            }
            if (request.getPassword() != null && request.getPassword().length() > 0) {
                customer.setPassword(request.getPassword());
            }
            assertEquals(customer.getName(), request.getName());
            assertEquals(customer.getEmail(), request.getEmail());
            assertEquals(customer.getPassword(), request.getPassword());
        }

        @Test
        void willThrowWhenCustomerDoesNotFoundWhenUpdate() {
            Long customerId=100L;
            assertThatThrownBy(() -> underTest.updateCustomer(customerId,new CustomerRequest()))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("Customer with id " + customerId + " doesn't exist");
            verify(customerRepository, never()).save(any());
        }
    }
}