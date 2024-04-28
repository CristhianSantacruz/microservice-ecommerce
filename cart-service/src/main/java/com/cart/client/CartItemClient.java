package com.cart.client;

import com.cart.dtos.CartItemDtoRequest;
import com.cart.dtos.CartItemDtoResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@RegisterRestClient(configKey = "cartItem-service")
@Path("/cart-item")
public interface CartItemClient {

    @GET
    @Path("/idCart/{idCart}")
    List<CartItemDtoResponse> getCartsItem(@PathParam("idCart")ObjectId idCart);

    @POST
    @Path("/save")
    CartItemDtoResponse save(CartItemDtoRequest cartItemDto);

    @GET
    @Path("/id/{id}")
    CartItemDtoResponse getCartItemById(@PathParam("id") ObjectId id);

}
