package com.cart.service;

import com.cart.exception.ProductRequestException;
import com.cart.model.CartEntity;
import org.bson.types.ObjectId;

public interface ICartService {


    CartEntity addItemToCart(ObjectId idProduct,ObjectId idCart) throws ProductRequestException;
    CartEntity createCart(String emailUser);
    boolean clearCart(ObjectId idCart);
    boolean deleteCart(ObjectId idCart);
    CartEntity getCart(ObjectId idCart);
}
