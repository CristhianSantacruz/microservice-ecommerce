package com.product.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class ProductDto {

    @NotNull
    @NotBlank
    private String nameProduct;
    @NotNull @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull @NotBlank
    private String category;

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
