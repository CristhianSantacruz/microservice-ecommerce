package com.product.healt;

import io.smallrye.health.checks.UrlHealthCheck;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class UserHealthCheck {

    @ConfigProperty(name = "user-service/mp-rest/url")
    String userServiceUrl;

    @Readiness
    HealthCheck checkUrl(){
        return new UrlHealthCheck(userServiceUrl+"/user/hello")
                .name("User Service").requestMethod(HttpMethod.GET)
                .statusCode(200);
    }
}
