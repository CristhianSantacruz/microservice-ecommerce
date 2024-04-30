package com.cartItem;

import com.cartItem.dtos.CarItemDto;
import com.cartItem.model.CartItemEntity;
import com.cartItem.service.ICartItemService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.util.Optional;

@Path("/cart-item")
public class CartItemResource {

    @Inject
    ICartItemService iCartItemService;

    @GET
    @Path("/hello")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @RolesAllowed({"admin"})
    @GET
    @Path("/all")
    public Response getAll() {
        return Response.ok(CartItemEntity.listAll()).build();
    }


    @POST
    @Path("/save")
    @Timeout(value = 3000L)
    @Fallback(fallbackMethod = "getFallBackProductById")
    public Response saveCartItem(CarItemDto carItemDto){
        return Response.status(201)
                .entity(iCartItemService.savecartItem(carItemDto)).build();

    }

    public Response getFallBackProductById(CarItemDto carItemDto) {
        return Response.ok("Lo sentimos el servicio product esta fallando").build();
    }

    @GET
    @RolesAllowed("user")
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

    @PATCH
    @RolesAllowed({"user"})
    @Path("/update-quantity/{id}")
    public Response updateVote(@PathParam("id") ObjectId id){
        return iCartItemService.updateQuantityCartItem(id) ? Response.ok().build()
                : Response.status(404).build();
    }

    @DELETE
    @RolesAllowed({"user","admin"})
    @Path("delete/{id}")
    public Response deleteCartItem(@PathParam("id") ObjectId id){
        return iCartItemService.deleteCartItemById(id) ? Response.ok().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
