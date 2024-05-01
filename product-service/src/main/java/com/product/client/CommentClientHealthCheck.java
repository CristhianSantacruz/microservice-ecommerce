package com.product.client;

import jakarta.inject.Inject;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Liveness
public class CommentClientHealthCheck implements HealthCheck {

    @Inject
    @RestClient
    CommentClient commentClient;


    @Override
    public HealthCheckResponse call() {
        commentClient.getAllCommentsByIdProduct(new ObjectId("662c6751f646327f332839a3"));
        return HealthCheckResponse.named("Comment By IdProduct").up().build();
    }
}
