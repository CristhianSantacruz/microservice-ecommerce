package com.product.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Collections;
import java.util.List;

@RegisterRestClient(configKey = "comment-service")
@Path("/comment")
public interface CommentClient {

    @GET
    @Path("/all")
    List<CommentDto> getAllComments();

    @GET
    @Timeout(value = 3000L)
    @Retry(maxRetries = 2)
    @Fallback(fallbackMethod = "fallbackGetAllCommentsByIdProduct")
    @Path("/all-by-idProduct/{idProduct}")
    List<CommentDto> getAllCommentsByIdProduct(@PathParam("idProduct") ObjectId idProduct);


    default List<CommentDto> fallbackGetAllCommentsByIdProduct(ObjectId idProduct) {
        return Collections.emptyList();
    }

}
