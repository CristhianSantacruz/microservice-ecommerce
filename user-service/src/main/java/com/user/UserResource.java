package com.user;

import com.user.model.UserEntity;
import com.user.service.IUserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.annotations.Param;

import java.util.Optional;

@Path("/user")
public class UserResource {

    @Inject
    IUserService userService;

    @GET
    @PermitAll
    @Path("/hello")
    public Response hello() {
        return Response.ok("Hello User Service!").build();
    }

    @PermitAll
    @GET
    @Path("/email/{email}")
    public Response getEmail(@PathParam("email") String email) {
        Optional<UserEntity> optionalUser = userService.getUserByEmail(email);
        return optionalUser.isPresent() ? Response.ok(optionalUser.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}
