package com.product;

import com.product.client.CommentClient;
import com.product.dto.ProductDto;
import com.product.model.ProductEntity;
import com.product.service.IProductService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.vertx.ext.web.handler.HttpException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.faulttolerance.exceptions.TimeoutException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Logger;

@Path("/product")
@Transactional
@Singleton
public class ProductResource {

    Logger LOGGER = Logger.getLogger(ProductResource.class.getName());

    @Inject
    IProductService iProductService;

    @Inject
    @RestClient
    CommentClient commentClient;

    private final MeterRegistry meterRegistry;


    private final LinkedHashSet<String> listNameSearch = new LinkedHashSet<>();


    ProductResource(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        meterRegistry.gaugeCollectionSize("list.size.name", Tags.of("gaugeSearch","gaugeNameSearch"), listNameSearch);
    }

    @GET
    @PermitAll
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        LOGGER.info("ENDPOINT HELLO");
        return Response.ok("Hello Product Service").build();
    }
    @PermitAll
    @GET
    @Path("/comments")
    public Response comments(){
        LOGGER.info("ENDPOINT COMMENTS");
        return Response.ok(this.commentClient.getAllComments()).build();
    }

    //save product
    @RolesAllowed("admin")
    @POST
    @Path("/save")
    public Response saveProduct(@Valid ProductEntity productEntity){
        this.LOGGER.info("ENDPOINT SAVE PRODUCT");
        return Response.status(201).entity(iProductService.saveProduct(productEntity)).build();
    }

    public Response saveFallBack() {
        return Response.ok("Hubo un problema al guardar el producto").build();
    }


    @GET
    @PermitAll
    @Timed(value = "call.all.product",extraTags = {"all","products"})
    @Path("/all")
    @Timeout(value = 3000L)
    @Fallback(fallbackMethod = "getAllFallBack",applyOn = TimeoutException.class)
    public Response getAllProducts(){
        this.LOGGER.info("ENDPOINT ALL PRODUCTS");
        return Response.ok(iProductService.getAllProducts()).build();
    }

    public Response getAllFallBack(){
        return Response.ok("Hubo un problema en mostrar los productos").build();
    }

    @PermitAll
    @GET
    @Path("/all-ascending-year")
    public Response getAllProductsAscendingYear(){
        LOGGER.info("ENDPOINT ALL PRODUCTS ASCENDING YEAR");
        return Response.ok(iProductService.getAllProductsAscendingByYear()).build();
    }

    @PermitAll
    @GET
    @Path("/all-descending-year")
    public Response getAllProductsDescendingYear(){
        LOGGER.info("ENDPOINT ALL PRODUCTS DESCENDING YEAR");
        return Response.ok(iProductService.getAllProductsDescendingByYear()).build();
    }

    @PermitAll
    @GET
    @Path("/all-ascending-name")
    public Response getAllProductsAscendingName(){
        LOGGER.info("ENDPOINT ALL PRODUCTS ASCENDING NAME");
        return Response.ok(iProductService.getAllProductsAscendingByName()).build();
    }

    @PermitAll
    @GET
    @Path("/all-descending-name")
    public Response getAllProductsDescendingName(){
        LOGGER.info("ENDPOINT ALL PRODUCTS DESCENDING NAME");
        return Response.ok(iProductService.getAllProductsDescendingByName()).build();
    }


    @PermitAll
    @GET
    @Path("/all-by-category/{category}")
    public Response getAllProductsByCategory(@PathParam("category") String category){
        LOGGER.info("ENDPOINT BY CATEGORY");
        return Response.ok(iProductService.getAllProductsByCategory(category)).build();
    }

    @PermitAll
    @GET
    @Path("/all-by-year/{year}")
    public Response getAllProductsByYear(@PathParam("year") int year){
        LOGGER.info("ENDPOINT BY YEAR");
        return Response.ok(iProductService.getAllProductsByYear(year)).build();
    }

    @PermitAll
    @GET
    @Path("/all-price-less/{price}")
    public Response getAllProductByPriceLess(@PathParam("price") double price){
        LOGGER.info("ENDPOINT PRICE LESS");
        return Response.ok(iProductService.getAllProductByPriceLees(price)).build();
    }

    @PermitAll
    @GET
    @Path("/all-price-more/{price}")
    public Response getAllProductByPriceMore(@PathParam("price") double price){
        LOGGER.info("ENDPOINT PRICE MORE");
        return Response.ok(iProductService.getAllProductByPriceMore(price)).build();
    }

    @PermitAll
    @GET
    @Timed(value = "call.product.id",extraTags = {"product","productId"})
    @Path("/id/{id}")
    public  Response getProductById(@PathParam("id")ObjectId id){
        LOGGER.info("GET PRODUCT BY ID");
        Optional<ProductEntity> pro0 = iProductService.getProductById(id);
        return pro0.isPresent() ? Response.ok(pro0.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @PermitAll
    @GET
    @Path("/sku/{sku}")
    public  Response getProductBSku(@PathParam("sku") String sku){
        LOGGER.info("GET PRODUCT BY SKU");
        Optional<ProductEntity> pro0 = iProductService.getProductBySku(sku);
        return pro0.isPresent() ? Response.ok(pro0.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @PermitAll
    @GET
    @Counted(value = "count.search",extraTags = {"search","search.product"})
    @Path("/search/")
    public Response searchByName(@QueryParam("name") String name){
        LOGGER.info("SEARCH PRODUCT BY NAME");
        this.listNameSearch.add(name);
        return Response.ok(iProductService.getProductsBySearchName(name)).build();
    }

    @RolesAllowed("admin")
    @PUT
    @Path("update/{id}")
    public Response updateProduct(@PathParam("id") ObjectId id, ProductDto productUpdateDto){
        this.LOGGER.info("UPDATE PRODUCT");
        Optional<ProductEntity> productUpdateDto1 = iProductService.updateProduct(id,productUpdateDto);

        return productUpdateDto1.isPresent() ? Response.ok(productUpdateDto1.get()).build() : Response.status(Response.Status.NOT_FOUND).build();

    }
    @RolesAllowed("admin")
    @DELETE
    @Path("/delete/{id}")
    public Response deleteProduct(@PathParam("id") ObjectId id){
        LOGGER.info("ENDPOINT DELETE PRODUCT");
        return iProductService.deleteProductById(id)
                ? Response.ok().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }


}
