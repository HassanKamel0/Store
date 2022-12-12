package com.example.demo.services.serviceImpl;
import com.example.demo.DTO.request.CartRequest;
import com.example.demo.DTO.response.CartResponse;
import com.example.demo.repository.CartRepository;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    public CartResponse getCart(Long cartId) {
        boolean exist=cartRepository.existsById(cartId);
        if(!exist)
            throw new IllegalStateException("Cart with id "+cartId+" doesn't exist");
        CartResponse response=new CartResponse();
        Cart cart=cartRepository.findById(cartId).get();
        response.setTotalPrice(cart.getTotalPrice());
        response.setCreatedDate(cart.getCreatedDate());
        return response;
    }
    public void postCart(CartRequest request) {
     Cart cart=new Cart();
     if(request.getTotalPrice()>0)
         cart.setTotalPrice(request.getTotalPrice());
     if(request.getCreatedDate().isBefore(LocalDate.now()) || request.getCreatedDate().isEqual(LocalDate.now()))
         cart.setCreatedDate(request.getCreatedDate());
     cartRepository.save(cart);
    }
    public void deleteCart(Long cartId) {
    boolean exist=cartRepository.existsById(cartId);
        if(!exist)
          throw  new IllegalStateException("Cart with id "+cartId+" doesn't exist");
    cartRepository.deleteById(cartId);
    }
    public void updateCart(Long cartId, CartRequest request) {
    Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new IllegalStateException(
            "Cart with id "+cartId+" doesn't exist"));
    if(request.getTotalPrice()>0)
        cart.setTotalPrice(request.getTotalPrice());
    if(request.getCreatedDate().isBefore(LocalDate.now()) || request.getCreatedDate().isEqual(LocalDate.now()))
        cart.setCreatedDate(request.getCreatedDate());
    cartRepository.save(cart);
    }
    public void createCartForCustomer(Customer customer){
        Cart cart=new Cart();
        cart.setCustomer(customer);
        cart.setCreatedDate(LocalDate.now());
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }
}