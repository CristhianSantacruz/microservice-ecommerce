package com.cart.client;

import com.cart.dtos.UserDtoResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "user-service")
@Path("/user")
public interface UserClient {

    @GET
    @Path("/email/{email}")
    UserDtoResponse getUserByEmail(@PathParam("email") String email);
}
