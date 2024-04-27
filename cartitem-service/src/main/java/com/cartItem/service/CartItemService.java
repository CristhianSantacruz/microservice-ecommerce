package com.cartItem.service;

import com.cartItem.clientProduct.ProductClient;
import com.cartItem.dtos.CarItemDto;
import com.cartItem.dtos.ProductDto;
import com.cartItem.model.CartItemEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class CartItemService implements ICartItemService{

    @Inject
    @RestClient
    ProductClient productClient;

    @Override
    public CartItemEntity savecartItem(CarItemDto carItemDto) {
        CartItemEntity cartItemEntity = new CartItemEntity();
        ProductDto productDto = productClient.getProductById(carItemDto.getIdProduct());
        cartItemEntity.setAddedDate(LocalDateTime.now());
        cartItemEntity.setQuantity(1);
        cartItemEntity.setProductDto(productDto);
        cartItemEntity.setPriceProduct(productDto.getPrice());
        cartItemEntity.setSubTotal(cartItemEntity.calculateSubTotal());
        cartItemEntity.persist();
        return cartItemEntity;
    }

    @Override
    public boolean updateQuantityCartItem(ObjectId id) {
        Optional<CartItemEntity> optionalCarItem = CartItemEntity.findByIdOptional(id);
        if (optionalCarItem.isPresent()) {
            CartItemEntity cartItemEntity = optionalCarItem.get();
            cartItemEntity.setQuantity(cartItemEntity.getQuantity()+1);
            cartItemEntity.setSubTotal(cartItemEntity.calculateSubTotal());
            cartItemEntity.update();
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal calculateSubTotal(double price, double quantity) {

            BigDecimal priceBigDecimal = BigDecimal.valueOf(price);
            BigDecimal quantityBigDecimal = BigDecimal.valueOf(quantity);
            return priceBigDecimal.multiply(quantityBigDecimal);


    }

    @Override
    public Optional<CartItemEntity> getCartItemById(ObjectId id) {
        return CartItemEntity.findByIdOptional(id);
    }

    @Override
    public boolean deleteCartItemById(ObjectId id) {
        return CartItemEntity.deleteById(id);
    }
}
