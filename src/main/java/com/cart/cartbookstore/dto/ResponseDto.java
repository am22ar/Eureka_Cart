package com.cart.cartbookstore.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseDto {
    public String message;
    public Object data;
    public String token;
}
