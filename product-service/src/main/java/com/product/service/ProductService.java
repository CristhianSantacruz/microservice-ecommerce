package com.product.service;

import com.product.client.CommentClient;
import com.product.dto.ProductDto;
import com.product.model.ProductEntity;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class ProductService implements IProductService {

    @Inject
    @RestClient
    CommentClient commentClient;

    @Override
    public Optional<ProductEntity> getProductById(ObjectId objectId) {
       Optional<ProductEntity> productEntity =  ProductEntity.findByIdOptional(objectId);
       if(productEntity.isPresent()) {
           ProductEntity product = productEntity.get();
           product.setCommentList(commentClient.getAllCommentsByIdProduct(product.id));
           product.update();
           return Optional.of(product);
       }
        return Optional.empty();
    }

    @Override
    public Optional<ProductEntity> getProductBySku(String sku) {
        return ProductEntity.find("skuProduct",sku).firstResultOptional();
    }

    @Override
    public List<ProductEntity> getProductsBySearchName(String searchName) {
        return ProductEntity.find("nameProduct like ?1",searchName).list();
    }

    @Override
    public List<ProductEntity> getAllProductsByCategory(String category) {
        return ProductEntity.list("category",category);
    }

    @Override
    public List<ProductEntity> getAllProductsByYear(int year) {
        return ProductEntity.list("year",year);
    }

    @Override
    public List<ProductEntity> getAllProductByPriceLees(double price) {
        return ProductEntity.list("price <= ?1",price);
    }


    @Override
    public List<ProductEntity> getAllProductByPriceMore(double price) {
        return ProductEntity.list("price >= ?1",price);
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return ProductEntity.listAll();
    }

    @Override
    public List<ProductEntity> getAllProductsAscendingByYear() {
        return ProductEntity.listAll(Sort.by("year").ascending());
    }

    @Override
    public List<ProductEntity> getAllProductsDescendingByYear() {
        return ProductEntity.listAll(Sort.by("year").descending());
    }

    @Override
    public List<ProductEntity> getAllProductsAscendingByName() {
        return ProductEntity.listAll(Sort.by("nameProduct").ascending());
    }

    @Override
    public List<ProductEntity> getAllProductsDescendingByName() {
        return ProductEntity.listAll(Sort.by("nameProduct").descending());
    }


    @Override
    public ProductEntity saveProduct(ProductEntity product) {
        LocalDateTime dateCreation  = LocalDateTime.now();
        product.setPrice(new BigDecimal(String.valueOf(product.getPrice())));
        product.setLocalDateTime(dateCreation);
        product.setYear(dateCreation.getYear());
        product.setSkuProduct(generateSkuProduct(product.getNameProduct(),product.getCategory()));
        product.setCommentList(new ArrayList<>());
        //save
        product.persist();
        return product;
    }

    @Override
    public Optional<ProductEntity> updateProduct(ObjectId id, ProductDto product) {
        Optional<ProductEntity> productOptional = ProductEntity.findByIdOptional(id);
        if(productOptional.isPresent()) {
            ProductEntity productToUpdate = productOptional.get();
            productToUpdate.setNameProduct(product.getNameProduct());
            productToUpdate.setCategory(product.getCategory());
            productToUpdate.setDescription(product.getDescription());
            productToUpdate.setPrice(product.getPrice());
            productToUpdate.update();
            return Optional.of(productToUpdate);

        }
        return Optional.empty();
    }


    @Override
    public boolean deleteProductById(ObjectId objectId) {
        return ProductEntity.deleteById(objectId);
    }

    @Override
    public String generateSkuProduct(String nameProduct, String category) {
       nameProduct  =  nameProduct.replaceAll("\\s+", "").toUpperCase();
        category = category.replaceAll("\\s+", "").toUpperCase();

        // Generar tres números aleatorios
        Random random = new Random();
        int randomNumber1 = random.nextInt(10); // Números entre 0 y 9
        int randomNumber2 = random.nextInt(10);
        int randomNumber3 = random.nextInt(10);

        // Combinar el nombre del producto, la categoría y los números aleatorios para formar el SKU

        return nameProduct.substring(0, Math.min(nameProduct.length(), 3)) + "-" +
                category.substring(0, Math.min(category.length(), 3)) + "-" +
                randomNumber1 + randomNumber2 + randomNumber3;
    }
}
