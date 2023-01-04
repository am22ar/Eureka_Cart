package com.cart.cartbookstore.model;

import com.cart.cartbookstore.dto.CartDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cart")
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;
    private Long userId;
    private Long bookId;
    private int quantity;

    public CartModel(CartDto cartDto) {
        this.userId = cartDto.getUserId();
        this.bookId = cartDto.getBookId();
        this.quantity = cartDto.getQuantity();
    }
//    public CartModel(CartDto cartDto,Long cartId ) {
//        this.cartId = cartId;
//        this.userId = cartDto.userId;
//        this.bookId = cartDto.bookId;
//        this.quantity = cartDto.quantity;
//    }
}
