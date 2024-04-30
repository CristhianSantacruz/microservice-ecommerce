package com.cart.exception;

public class ProductRequestException extends RuntimeException{

    ProductRequestException(){
        super("Product request failed");
    }
}
