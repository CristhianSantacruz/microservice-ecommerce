package com.cart.model;

import com.cart.dtos.CartItemDtoResponse;
import com.cart.dtos.UserDtoResponse;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@MongoEntity(collection = "carts")
public class CartEntity extends PanacheMongoEntity {

    private UserDtoResponse userDtoResponse;
    private List<CartItemDtoResponse> cartItemList  =  new ArrayList<>();
    private LocalDateTime createdAt;
    private BigDecimal totalPrice;

    public List<CartItemDtoResponse> getCartItemList() {
        return cartItemList;
    }

    public UserDtoResponse getUserDtoResponse() {
        return userDtoResponse;
    }

    public void setUserDtoResponse(UserDtoResponse userDtoResponse) {
        this.userDtoResponse = userDtoResponse;
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
