package com.cartItem;

import com.cartItem.dtos.CarItemDto;
import com.cartItem.model.CartItemEntity;
import com.cartItem.service.ICartItemService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.Optional;

@Path("/cart-item")
public class CartItemResource {

    @Inject
    ICartItemService iCartItemService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }


    @POST
    @Path("/save")
    public Response saveCartItem(CarItemDto carItemDto){
        return Response.status(201)
                .entity(iCartItemService.savecartItem(carItemDto)).build();

    }
    @GET
    @Path("/idCart/{idCart}")
    public Response getAllByIdCart(@PathParam("idCart") ObjectId idCart){
        return Response.ok(iCartItemService.getAllByIdCart(idCart)).build();
    }

    @GET
    @Path("/id/{id}")
    public Response getCartItemById(@PathParam("id") ObjectId id){
        Optional<CartItemEntity> optionalCartItem = iCartItemService.getCartItemById(id);

        return optionalCartItem.isPresent()
                ? Response.ok(optionalCartItem.get()).build()
                : Response.status(404).build();
    }

    @PUT
    @Path("/update-quantity/{id}")
    public Response updateVote(@PathParam("id") ObjectId id){
        return iCartItemService.updateQuantityCartItem(id) ? Response.ok().build()
                : Response.status(404).build();
    }

    @DELETE
    @Path("delete/{id}")
    public Response deleteCartItem(@PathParam("id") ObjectId id){
        return iCartItemService.deleteCartItemById(id) ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
