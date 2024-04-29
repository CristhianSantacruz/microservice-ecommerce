package com.cartItem.clientProduct;

import com.cartItem.dtos.ProductDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Optional;

@Path("/product")
@RegisterRestClient(configKey = "product-service")
public interface ProductClient {

    @GET
    @Path("/id/{id}")
    ProductDto getProductById(@PathParam("id")ObjectId id);


}
