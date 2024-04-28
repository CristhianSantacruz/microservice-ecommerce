package com.cart;

import com.cart.model.CartEntity;
import com.cart.service.CartService;
import com.cart.service.ICartService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.io.ObjectInput;

@Path("/cart")
@Transactional
public class CartResource {

    @Inject
    ICartService iCartService;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    @PermitAll
    public Response hello() {
        return Response.ok("Hello from Quarkus REST").build();
    }

    @GET
    @RolesAllowed("user")
    @Path("/id/{id}")
    public Response getCartById(@PathParam("id") ObjectId id) {
        return Response.ok(iCartService.getCart(id)).build();
    }

    @POST
    @RolesAllowed("user")
    @Path("/create")
    public Response createCart(){
        return Response.status(201).entity(iCartService.createCart()).build();
    }

    @POST
    @RolesAllowed("user")
    @Path("/add/{idProduct}/cart/{idCart}")
    public Response addItem(@PathParam("idProduct") ObjectId idProduct, @PathParam("idCart") ObjectId idCart ) {
        return Response.ok(iCartService.addItemToCart(idProduct,idCart)).build();
    }

    @PUT
    @RolesAllowed("user")
    @Path("/clear/{idCart}")
    public Response clearCart(@PathParam("idCart") ObjectId idCart ) {
        return iCartService.clearCart(idCart) ? Response.ok().build() : Response.status(Response.Status.BAD_REQUEST).build();
    }


    @DELETE
    @RolesAllowed("user")
    @Path("/delete/{id}")
    public Response deleteItem(@PathParam("id") ObjectId id) {
        return iCartService.deleteCart(id) ? Response.ok(true).build() : Response.status(Response.Status.NOT_FOUND).entity(false).build();
    }
}
