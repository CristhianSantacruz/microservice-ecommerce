package com.cart;

import com.cart.exception.ProductRequestException;
import com.cart.model.CartEntity;
import com.cart.service.CartService;
import com.cart.service.ICartService;
import io.smallrye.mutiny.TimeoutException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

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
    @Path("/create/{emailUser}")
    public Response createCart(@PathParam("emailUser") String emailUser){
        return Response.status(201).entity(iCartService.createCart(emailUser)).build();
    }

    @POST
    @RolesAllowed("user")
    @Path("/add/{idProduct}/cart/{idCart}")
    @Fallback(fallbackMethod = "fallBackAddMethod")
    public Response addItem(@PathParam("idProduct") ObjectId idProduct, @PathParam("idCart") ObjectId idCart ) {
        return Response.ok(iCartService.addItemToCart(idProduct,idCart)).build();
    }

    public Response fallBackAddMethod(ObjectId idProduct, ObjectId idCart) {
        return Response.ok("Service product not found " +idProduct).build();
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
