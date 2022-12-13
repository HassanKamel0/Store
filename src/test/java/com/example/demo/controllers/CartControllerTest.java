package com.example.demo.controllers;
import java.net.URI;
import java.time.LocalDate;
import com.example.demo.DTO.request.CartRequest;
import com.example.demo.entity.Cart;
import com.example.demo.services.serviceImpl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import com.example.demo.DemoApplication;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {
    @MockBean CartServiceImpl cartService;
    @LocalServerPort
    int randomServerPort;
    @Test
    void shouldGetCart() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/cart/1");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Cart> responseCart = restTemplate.getForEntity(uri,Cart.class);
        assertEquals(HttpStatus.OK,responseCart.getStatusCode());
    }
    @Test
    void shouldPostCart() throws Exception {
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/cart");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Cart> createdCart = restTemplate.postForEntity(uri,setCartRequest(), Cart.class);
        assertThat(createdCart.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    @Test
    void shouldDeleteCart() throws Exception {
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/cart/1");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.delete(uri);
    }
    @Test
    void shouldUpdateCart() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/cart/1");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<CartRequest>(setCartRequest(), new HttpHeaders()), Void.class);
    }
    private static CartRequest setCartRequest() {
        CartRequest request=new CartRequest();
        request.setCreatedDate(LocalDate.now());
        request.setTotalPrice(123);
        return request;
    }
}