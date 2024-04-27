package com.product.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@MongoEntity(collection = "product")
public class ProductEntity extends PanacheMongoEntity {
    @NotNull @NotBlank
    private String nameProduct;
    @NotNull @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull @NotBlank
    private String category;
    private String imageUrl;
    private String skuProduct;
    private LocalDateTime localDateTime;
    private int year;
    //private List<Comment> commentList;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSkuProduct() {
        return skuProduct;
    }

    public void setSkuProduct(String skuProduct) {
        this.skuProduct = skuProduct;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
