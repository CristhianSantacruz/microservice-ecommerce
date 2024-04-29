package com.cartItem.model;

import com.cartItem.dtos.ProductDto;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@MongoEntity(collection = "cartItem")
public class CartItemEntity extends PanacheMongoEntity {
    private ProductDto productDto;
    private int quantity;
    private double priceProduct;
    private BigDecimal subTotal;
    private LocalDateTime addedDate;


    public ProductDto getProductDto() {
        return productDto;
    }

    public BigDecimal calculateSubTotal() {

        BigDecimal priceBigDecimal = BigDecimal.valueOf(this.priceProduct);
        BigDecimal quantityBigDecimal = BigDecimal.valueOf(this.quantity);
        return priceBigDecimal.multiply(quantityBigDecimal);


    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(double priceProduct) {
        this.priceProduct = priceProduct;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }
}
