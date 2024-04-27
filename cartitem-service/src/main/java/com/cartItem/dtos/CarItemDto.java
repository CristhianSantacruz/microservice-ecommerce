package com.cartItem.dtos;

import org.bson.types.ObjectId;

public class CarItemDto {

    private ObjectId idProduct;

    public ObjectId getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(ObjectId idProduct) {
        this.idProduct = idProduct;
    }

}
