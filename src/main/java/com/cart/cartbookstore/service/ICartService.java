package com.cart.cartbookstore.service;

import com.cart.cartbookstore.dto.CartDto;
import com.cart.cartbookstore.model.CartModel;

import java.util.List;

public interface ICartService {
    public CartModel addToCart(CartDto cartDto);
    public List<CartModel> getAll();
    public CartModel getById(Long cartId);
    public CartModel updateCartByID(CartDto cartDto, Long cartId);
    public CartModel updateCartQuantity(CartDto cartDto,Long cartId,int quantity);
    public String deleteCartId(long cartId);
}
