package com.example.demo.controllers;
import com.example.demo.DTO.request.LineItemRequest;
import com.example.demo.DTO.request.ProductRequest;
import com.example.demo.DemoApplication;
import com.example.demo.entity.Product;
import com.example.demo.services.serviceImpl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
    @MockBean ProductServiceImpl productService;
    @LocalServerPort
    int randomServerPort;
    @Test
    void shouldGetProduct() throws Exception{
        URI uri=new URI("http://localhost:"+randomServerPort+"/api/v1/product/1");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Product> responseProduct=restTemplate.getForEntity(uri, Product.class);
        assertEquals(HttpStatus.OK,responseProduct.getStatusCode());
    }
    @Test
    void shouldPostProduct() throws Exception{
        URI uri=new URI("http://localhost:"+randomServerPort+"/api/v1/product");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<Product> createdProduct=restTemplate.postForEntity(uri, setProductRequest(),Product.class);
        assertEquals(HttpStatus.CREATED,createdProduct.getStatusCode());
    }
    @Test
    void shouldPostProductInLineItem() throws Exception{
        URI uri=new URI("http://localhost:"+randomServerPort+"/api/v1/product/1");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<LineItemRequest> createdProduct=restTemplate.postForEntity(uri, setLineItemRequest(),LineItemRequest.class);
        assertEquals(HttpStatus.CREATED,createdProduct.getStatusCode());
    }
    @Test
    void shouldDeleteProduct() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/product/1");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.delete(uri);
    }
    @Test
    void shouldUpdateProduct() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/product/1");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<ProductRequest>(setProductRequest(), new HttpHeaders()), Void.class);
    }
    private static ProductRequest setProductRequest() {
        ProductRequest request=new ProductRequest();
        request.setName("product");
        request.setPrice(123);
        return request;
    }
    private static LineItemRequest setLineItemRequest() {
        LineItemRequest request=new LineItemRequest();
        request.setQuantity(123);
        request.setProductId(1L);
        return request;
    }
}