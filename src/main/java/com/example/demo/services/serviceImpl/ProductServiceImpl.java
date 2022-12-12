package com.example.demo.services.serviceImpl;
import com.example.demo.DTO.request.ProductRequest;
import com.example.demo.DTO.request.ProductLineItemRequest;
import com.example.demo.DTO.response.ProductResponse;
import com.example.demo.repository.ProductRepository;
import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import com.example.demo.services.serviceImpl.LineItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired private ProductRepository productRepository;
    @Autowired private LineItemServiceImpl lineItemServiceImpl;
    public ProductResponse getProduct(Long productId) {
        boolean exist=productRepository.existsById(productId);
        if(!exist)
            throw new IllegalStateException("Product with id "+productId+" doesn't exist");
        ProductResponse response=new ProductResponse();
        Product product=productRepository.findById(productId).get();
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        return response;
    }
    public void postProduct(ProductRequest productRequest) {
        Optional<Product> productOptional=productRepository
                .findProductByName(productRequest.getName());
        if(productOptional.isPresent())
            throw new IllegalStateException("This product is already here");
        Product product= new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
    }
    public void deleteProduct(Long productId) {
        boolean exist=productRepository.existsById(productId);
        if(!exist)
            throw new IllegalStateException("Product with id "+productId+" doesn't exist");
        productRepository.deleteById(productId);
    }
    public void updateProduct(Long productId, ProductRequest request) {
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with id " + productId + " doesn't exist"));
        if(request.getName()!=null && request.getName().length()>0)
            product.setName(request.getName());
        if(request.getPrice()>0)
            product.setPrice(request.getPrice());
        productRepository.save(product);
    }
    public void postProductInLineItem(ProductLineItemRequest lineItemRequest) {
        lineItemServiceImpl.putProductInLineItem(lineItemRequest);
    }
}