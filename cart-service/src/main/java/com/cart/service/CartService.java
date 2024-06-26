package com.cart.service;

import com.cart.client.CartItemClient;
import com.cart.client.UserClient;
import com.cart.dtos.CartItemDtoRequest;
import com.cart.dtos.CartItemDtoResponse;
import com.cart.dtos.UserDtoResponse;
import com.cart.exception.ProductRequestException;
import com.cart.model.CartEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CartService implements ICartService {

    @Inject
    @RestClient
    CartItemClient cartItemClient;

    @Inject
    @RestClient
    UserClient userClient;


    @Override
    public CartEntity createCart(String emailUser) {
        CartEntity cart = new CartEntity();
        //guardar el dieno del carro osea el usuario logeado
        cart.setCreatedAt(LocalDateTime.now());
        cart.setCartItemList(new ArrayList<>());
        cart.setTotalPrice(BigDecimal.ZERO);
        UserDtoResponse userDtoResponse = userClient.getUserByEmail(emailUser);
        cart.setUserDtoResponse(userDtoResponse);
        cart.persist();
        return cart;
    }


    @Override
    public CartEntity addItemToCart(ObjectId idProduct,ObjectId idCart) {
        CartEntity cart = CartEntity.findById(idCart);
        if (cart == null) {
            // No se encontró un carrito existente, crea uno nuevo
            cart = new CartEntity();
            cart.setCreatedAt(LocalDateTime.now());
            cart.setCartItemList(new ArrayList<>());
            cart.setTotalPrice(BigDecimal.ZERO);
            cart.persist();
        }
        // Agrega el nuevo ítem al carrito
        try {
            cart.getCartItemList().add(cartItemClient.save(new CartItemDtoRequest(idProduct)));
        }catch (ProductRequestException exception){
            System.err.println(exception.getMessage());
        }
        cart.update();
        return cart;
    }

    @Override
    public boolean clearCart(ObjectId idCart) {
        Optional<CartEntity> optionalCart = CartEntity.findByIdOptional(idCart);
        if (optionalCart.isPresent()) {
            CartEntity cart = optionalCart.get();
            cart.getCartItemList().clear();
            cart.update();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCart(ObjectId idCart) {
        return CartEntity.deleteById(idCart);
    }

    @Override
    public CartEntity getCart(ObjectId idCart) {
        CartEntity cart =  CartEntity.findById(idCart);
        List<CartItemDtoResponse> cartItems = cart.getCartItemList();
        List<CartItemDtoResponse> newCartItems = new ArrayList<>();
        for (CartItemDtoResponse cartItemDtoResponse : cartItems) {
            newCartItems.add(cartItemClient.getCartItemById(cartItemDtoResponse.getId()));
        }
        cart.setCartItemList(newCartItems);
        cart.setTotalPrice(calcualateTotalPrice(cart.getCartItemList()));
        cart.update();
        return cart;
    }

    public BigDecimal calcualateTotalPrice(List<CartItemDtoResponse> cartItemDtoResponses){
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItemDtoResponse cartItemDto : cartItemDtoResponses) {
            BigDecimal subTotal = cartItemDto.getSubTotal();
            totalPrice = totalPrice.add(subTotal);
        }

        return totalPrice;

    }
}
