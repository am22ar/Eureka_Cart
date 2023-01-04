package com.cart.cartbookstore.service;

import com.cart.cartbookstore.dto.CartDto;
import com.cart.cartbookstore.exception.BookStoreException;
import com.cart.cartbookstore.model.BookModel;
import com.cart.cartbookstore.model.CartModel;
import com.cart.cartbookstore.model.UserModel;
import com.cart.cartbookstore.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService{
    //rest template to achieve connection between different databases of different projects
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CartRepository cartRepository;

    @Override
    public CartModel addToCart(CartDto cartDto){
        //passing the localhost url along with GetMapping api's('getRecordById','getBookById') of user and book to fetch id from user and book table
        UserModel userModel = restTemplate.getForObject("http://localhost:8081/bookstore/getRecordById/"+cartDto.getUserId(), UserModel.class);
        BookModel bookModel = restTemplate.getForObject("http://localhost:8082/bookstore/getBookById/"+cartDto.getBookId(), BookModel.class);
        if(userModel.equals(null) || bookModel.equals(null))
            throw new BookStoreException("Either UserId or BookId is not found");
        else {
            CartModel cartModel = new CartModel(cartDto);
            return cartRepository.save(cartModel);
        }
    }
    @Override
    public List<CartModel> getAll() {
        List<CartModel> cartModelList = cartRepository.findAll();
        return cartModelList;
    }

    @Override
    public CartModel getById(Long cartId) {
        Optional<CartModel> cartModel = cartRepository.findById(cartId);
        if(cartModel.isPresent()){
            return cartModel.get();
        }else {
            throw new BookStoreException("Cart with Id: '"+cartId+"' not found");
        }
    }
    @Override
    public CartModel updateCartByID(CartDto cartDto, Long cartId){
        UserModel checkUserId = restTemplate.getForObject("http://localhost:8081/bookstore/getRecordById/"+cartDto.getUserId(),UserModel.class);
        BookModel checkBookId =  restTemplate.getForObject("http://localhost:8082/bookstore/getBookById/"+cartDto.getBookId(),BookModel.class);
        Optional<CartModel> checkCartId = cartRepository.findById(cartId);
        if(checkCartId.isPresent()){
            if(checkUserId!=null && checkBookId!=null){
                CartModel cartModel = new CartModel(cartDto);
                cartModel.setCartId(cartId);
                return cartRepository.save(cartModel);
            }else {
                throw new BookStoreException("Either UserId or BookId is not found! Update Failed");
            }
        }else {
            throw new BookStoreException("CartId: "+cartId+" not found..");
        }
    }
    @Override

    public CartModel updateCartQuantity(CartDto cartDto,Long cartId,int quantity){
        UserModel checkCartId = restTemplate.getForObject("http://localhost:8081/bookstore/getRecordById/"+cartDto.getUserId(),UserModel.class);
        BookModel checkBookId = restTemplate.getForObject("http://localhost:8082/bookstore/getBookById/"+cartDto.getBookId(),BookModel.class);
        int bookQuantity = checkBookId.getQuantity();
        if (checkCartId!=null) {
            if (quantity < bookQuantity) {
                CartModel cartModel = new CartModel(cartDto);
                cartModel.setCartId(cartId);
                cartModel.setQuantity(quantity);
                cartModel.setUserId(checkCartId.getUserId());
                cartModel.setBookId(checkBookId.getBookId());
                return cartRepository.save(cartModel);
            } else {
                throw new BookStoreException("Requested Quantity: " + quantity + " available Quantity: " + bookQuantity);
            }
        }else {
            throw new BookStoreException("CartId: "+checkCartId+" not found");
        }
    }
    @Override
    public String deleteCartId(long cartId){
        Optional<CartModel> cartData = cartRepository.findById(cartId);
        if(cartData.isPresent()){
            cartRepository.deleteById(cartId);
        }else {
            throw new BookStoreException("CartId: "+cartId+" not found");
        }
        return "CartId"+cartId+" deleted Successfully..";
    }
}
