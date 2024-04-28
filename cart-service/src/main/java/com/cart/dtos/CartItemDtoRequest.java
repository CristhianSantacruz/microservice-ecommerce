package com.cart.dtos;

import org.bson.types.ObjectId;

public class CartItemDtoRequest {

    private ObjectId idProduct;

    public CartItemDtoRequest(ObjectId idProduct) {
        this.idProduct = idProduct;
    }

    public ObjectId getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(ObjectId idProduct) {
        this.idProduct = idProduct;
    }
}
