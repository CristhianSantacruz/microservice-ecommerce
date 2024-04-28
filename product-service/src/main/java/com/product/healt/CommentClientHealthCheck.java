package com.product.healt;

import io.smallrye.health.checks.UrlHealthCheck;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class CommentClientHealthCheck {

    @ConfigProperty(name = "comment-service/mp-rest/url")
    String commentService;

    @Readiness
    HealthCheck checkUrl(){
        return new UrlHealthCheck(commentService+"/comment/hello").name("Comment Service").requestMethod(HttpMethod.GET).statusCode(200);
    }
}
