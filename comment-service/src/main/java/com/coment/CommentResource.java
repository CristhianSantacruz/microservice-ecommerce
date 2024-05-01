package com.coment;

import com.coment.dto.CommentDto;
import com.coment.model.CommentEntity;
import com.coment.service.ICommentService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.util.Optional;

@Path("/comment")
@Transactional

public class CommentResource {

    @Inject
    ICommentService iCommentService;

    @PermitAll
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        return Response.ok("Hello from Quarkus REST").build();
    }

    @RolesAllowed({"user","admin"})
    @POST
    @Path("/save")
    public Response saveComment(CommentDto comment) {

        return Response.status(201).entity(iCommentService.saveComment(comment)).build();
    }

    @PermitAll
    @GET
    @Path("/all")
    public Response getAllCommentsB() {
        return Response.ok(iCommentService.getAllComments()).build();
    }
    @PermitAll
    @GET
    @Path("/all-by-idProduct/{idProduct}")
    public Response getAllCommentsByIdProduct(@PathParam("idProduct") String idProduct) {
        return Response.ok(iCommentService.getAllCommentByIdProduct(idProduct)).build();
    }

    @RolesAllowed({"user","admin"})
    @GET
    @Path("/all-by-user/{emailUser}")
    public Response getAllCommentsByEmailUser(@PathParam("emailUser") String emailUser){
        return  Response.ok(iCommentService.getAllCommentsByUser(emailUser)).build();
    }


    @RolesAllowed({"user","admin"})
    @PUT
    @Path("/update/{idComment}")
    public Response updateComment(@PathParam("idComment") ObjectId idComment,CommentEntity commentEntity) {
        Optional<CommentEntity> comment = iCommentService.updateComment(idComment, commentEntity);
        return comment.isPresent() ? Response.ok(comment.get()).build()
                : Response.status(404).build();
    }

    @RolesAllowed({"user","admin"})
    @PATCH
    @Path("/update/{idComment}/calification/{calification}")
    public  Response updateCalification(@PathParam("idComment") ObjectId idComment,@PathParam("calification") int calification){
        return iCommentService.updateCalification(idComment,calification) ? Response.ok().build()
                : Response.status(404).build();
    }

    @RolesAllowed({"user","admin"})
    @PATCH
    @Path("/vote/{idComment}")
    public  Response updateVote(@PathParam("idComment") ObjectId idComment){
        return iCommentService.voteComment(idComment) ? Response.ok().build()
                : Response.status(404).build();
    }

    @RolesAllowed({"user","admin"})
    @DELETE
    @Path("/delete/{idComment}")
    public Response deleteComment(@PathParam("idComment") ObjectId idComment) {
        return iCommentService.deleteComment(idComment) ?
                Response.ok().build() : Response.status(404).build();
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/delete/all")
    public void deleteAllComments() {
        CommentEntity.deleteAll();
    }

}
