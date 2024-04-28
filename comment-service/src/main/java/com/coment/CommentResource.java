package com.coment;

import com.coment.model.CommentEntity;
import com.coment.service.ICommentService;
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

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello() {
        return Response.ok("Hello from Quarkus REST").build();
    }

    @POST
    @Path("/save")
    public Response saveComment(CommentEntity comment) {
        iCommentService.saveComment(comment);
        return Response.status(201).entity(comment).build();
    }
    @GET
    @Path("/all")
    public Response getAllCommentsB() {
        return Response.ok(iCommentService.getAllComments()).build();
    }

    @GET
    @Path("/all-by-idProduct/{idProduct}")
    public Response getAllCommentsByIdProduct(@PathParam("idProduct") String idProduct) {
        return Response.ok(iCommentService.getAllCommentByIdProduct(idProduct)).build();
    }
    @PUT
    @Path("/update/{idComment}")
    public Response updateComment(@PathParam("idComment") ObjectId idComment,CommentEntity commentEntity) {
        Optional<CommentEntity> comment = iCommentService.updateComment(idComment, commentEntity);
        return comment.isPresent() ? Response.ok(comment.get()).build()
                : Response.status(404).build();
    }
    @PUT
    @Path("/update/{idComment}/calification/{calification}")
    public  Response updateCalification(@PathParam("idComment") ObjectId idComment,@PathParam("calification") int calification){
        return iCommentService.updateCalification(idComment,calification) ? Response.ok().build()
                : Response.status(404).build();
    }

    @PUT
    @Path("/vote/{idComment}")
    public  Response updateVote(@PathParam("idComment") ObjectId idComment){
        return iCommentService.voteComment(idComment) ? Response.ok().build()
                : Response.status(404).build();
    }

    @DELETE
    @Path("/delete/{idComment}")
    public Response deleteComment(@PathParam("idComment") ObjectId idComment) {
        return iCommentService.deleteComment(idComment) ?
                Response.ok().build() : Response.status(404).build();
    }

    @DELETE
    @Path("/delete/all")
    public void deleteAllComments() {
        CommentEntity.deleteAll();
    }

}
