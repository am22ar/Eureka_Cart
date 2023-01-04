package com.cart.cartbookstore.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    @NotNull(message = "User Id can not be null!")
    public Long userId;
    @NotNull(message = "Book Id can not be null!")
    public Long bookId;
    @NotNull(message = "Quantity can not be null!")
    public int quantity;
}
