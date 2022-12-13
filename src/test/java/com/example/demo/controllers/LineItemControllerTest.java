package com.example.demo.controllers;
import com.example.demo.DTO.request.LineItemRequest;
import com.example.demo.DemoApplication;
import com.example.demo.entity.LineItem;
import com.example.demo.services.serviceImpl.LineItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LineItemControllerTest {
    @MockBean LineItemServiceImpl lineItemService;
    @LocalServerPort
    int randomServerPort;
    @Test
    void shouldGetLineItem() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/lineItem/1");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<LineItem> responseLineItem = restTemplate.getForEntity(uri,LineItem.class);
        assertEquals(HttpStatus.OK,responseLineItem.getStatusCode());
    }
    @Test
    void shouldPostLineItem() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/lineItem");
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<LineItem> responseLineItem = restTemplate.postForEntity(uri, setLineItemRequest(), LineItem.class);
        assertThat(responseLineItem.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    @Test
    void shouldDeleteLineItem() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/lineItem/1");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.delete(uri);
    }
    @Test
    void shouldUpdateLineItem() throws Exception{
        URI uri = new URI("http://localhost:" + randomServerPort + "/api/v1/lineItem/1");
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<LineItemRequest>(setLineItemRequest(), new HttpHeaders()), Void.class);
    }
    private static LineItemRequest setLineItemRequest() {
        LineItemRequest request=new LineItemRequest();
        request.setQuantity(123);
        request.setProductId(1L);
        return request;
    }
}