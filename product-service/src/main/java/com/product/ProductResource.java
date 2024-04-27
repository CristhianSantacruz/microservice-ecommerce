package com.product;

import com.product.dto.ProductDto;
import com.product.model.ProductEntity;
import com.product.service.IProductService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.Optional;

@Path("/product")
@Transactional
@Singleton
public class ProductResource {

    @Inject
    IProductService iProductService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public  String hello() {
        return "Hello Product Service";
    }
    //save product
    @POST
    @Path("/save")
    public Response saveProduct(@Valid ProductEntity productEntity){
        return Response.status(201).entity(iProductService.saveProduct(productEntity)).build();
    }
    @GET
    @Path("/all")
    public Response getAllProducts(){return Response.ok(iProductService.getAllProducts()).build();}
    @GET
    @Path("/all-ascending-year")
    public Response getAllProductsAscendingYear(){return Response.ok(iProductService.getAllProductsAscendingByYear()).build();}
    @GET
    @Path("/all-descending-year")
    public Response getAllProductsDescendingYear(){return Response.ok(iProductService.getAllProductsDescendingByYear()).build();}
    @GET
    @Path("/all-ascending-name")
    public Response getAllProductsAscendingName(){return Response.ok(iProductService.getAllProductsAscendingByName()).build();}
    @GET
    @Path("/all-descending-name")
    public Response getAllProductsDescendingName(){return Response.ok(iProductService.getAllProductsDescendingByName()).build();}
    @GET
    @Path("/all-by-category/{category}")
    public Response getAllProductsByCategory(@PathParam("category") String category){
        return Response.ok(iProductService.getAllProductsByCategory(category)).build();
    }
    @GET
    @Path("/all-by-year/{year}")
    public Response getAllProductsByYear(@PathParam("year") int year){
        return Response.ok(iProductService.getAllProductsByYear(year)).build();
    }

    @GET
    @Path("/all-price-less/{price}")
    public Response getAllProductByPriceLess(@PathParam("price") double price){
        return Response.ok(iProductService.getAllProductByPriceLees(price)).build();
    }
    @GET
    @Path("/all-price-more/{price}")
    public Response getAllProductByPriceMore(@PathParam("price") double price){
        return Response.ok(iProductService.getAllProductByPriceMore(price)).build();
    }
    @GET
    @Path("/id/{id}")
    public  Response getProductById(@PathParam("id")ObjectId id){
        Optional<ProductEntity> pro0 = iProductService.getProductById(id);
        return pro0.isPresent() ? Response.ok(pro0.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/sku/{sku}")
    public  Response getProductBSku(@PathParam("sku") String sku){
        Optional<ProductEntity> pro0 = iProductService.getProductBySku(sku);
        return pro0.isPresent() ? Response.ok(pro0.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/search/")
    public Response searchByName(@QueryParam("name") String name){
        return Response.ok(iProductService.getProductsBySearchName(name)).build();
    }

    @PUT
    @Path("update/{id}")
    public Response updateProduct(@PathParam("id") ObjectId id, ProductDto productUpdateDto){
        Optional<ProductEntity> productUpdateDto1 = iProductService.updateProduct(id,productUpdateDto);

        return productUpdateDto1.isPresent() ? Response.ok(productUpdateDto1.get()).build() : Response.status(Response.Status.NOT_FOUND).build();

    }

    @DELETE
    @Path("/delete/{id}")
    public Response deleteProduct(@PathParam("id") ObjectId id){
        return iProductService.deleteProductById(id)
                ? Response.ok().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

}
