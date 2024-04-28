package com.cart.model;

import com.cart.dtos.CartItemDtoResponse;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MongoEntity(collection = "carts")
public class CartEntity extends PanacheMongoEntity {

    //private String username;
    private List<CartItemDtoResponse> cartItemList  =  new ArrayList<>();
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;

    public List<CartItemDtoResponse> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItemDtoResponse> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
