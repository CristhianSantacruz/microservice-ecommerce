package com.user;

import com.user.dto.AuthRequest;
import com.user.dto.AuthResponse;
import com.user.model.UserEntity;
import com.user.service.IUserService;
import com.user.utils.TokenService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("/user")
@Transactional
public class AuthenticationResource {

    @Inject
    TokenService tokenService;

    @Inject
    IUserService iUserService;

    @PermitAll
    @GET
    @Path("/hello")
    public Response hello(){
        return Response.ok("Hello from User").build();
    }

    @PermitAll
    @POST
    @Path("/login") @Produces({MediaType.APPLICATION_JSON}) @Consumes(MediaType.APPLICATION_JSON)
    public Response login(AuthRequest authRequest) {

        Optional<UserEntity> userEntity = iUserService.getUserByEmail(authRequest.email);
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            return iUserService.passwordLoginEquals(authRequest.password,user.getPassword()) ?
                    Response.ok(new AuthResponse(tokenService.generateToken(user.getUsername(),user.getRol().toString()))).build() : Response.status(Response.Status.BAD_REQUEST).build();

        }
        return Response.status(Response.Status.NOT_FOUND).entity("Not exists user").build();

    }

    @PermitAll
    @POST
    @Path("/register") @Produces({MediaType.APPLICATION_JSON}) @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Valid UserEntity userEntity){
        return Response.status(201).entity(iUserService.saveUser(userEntity)).build();
    }
}
