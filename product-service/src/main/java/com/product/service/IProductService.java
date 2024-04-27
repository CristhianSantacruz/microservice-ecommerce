package com.product.service;

import com.product.dto.ProductDto;
import com.product.model.ProductEntity;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Optional<ProductEntity> getProductById(ObjectId objectId);
    Optional<ProductEntity> getProductBySku(String sku);

    List<ProductEntity> getProductsBySearchName(String searchName);
    List<ProductEntity> getAllProductsByCategory(String category);
    List<ProductEntity> getAllProductsByYear(int year);
    List<ProductEntity> getAllProductByPriceLees(double price);
    List<ProductEntity> getAllProductByPriceMore(double price);
    List<ProductEntity> getAllProducts();
    List<ProductEntity> getAllProductsAscendingByYear();
    List<ProductEntity> getAllProductsDescendingByYear();
    List<ProductEntity> getAllProductsAscendingByName();
    List<ProductEntity> getAllProductsDescendingByName();

    ProductEntity saveProduct(ProductEntity product);
    Optional<ProductEntity> updateProduct(ObjectId id, ProductDto product);
    boolean deleteProductById(ObjectId objectId);

    String generateSkuProduct(String nameProduct,String category);
}
