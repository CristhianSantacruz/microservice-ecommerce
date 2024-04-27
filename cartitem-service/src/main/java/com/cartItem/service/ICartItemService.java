package com.cartItem.service;

import com.cartItem.dtos.CarItemDto;
import com.cartItem.model.CartItemEntity;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Optional;

public interface ICartItemService {


    CartItemEntity savecartItem(CarItemDto carItemDto);
    boolean updateQuantityCartItem(ObjectId idCartItem);
    BigDecimal calculateSubTotal(double price, double quantity);
    Optional<CartItemEntity> getCartItemById(ObjectId id);
    boolean deleteCartItemById(ObjectId id);

}
