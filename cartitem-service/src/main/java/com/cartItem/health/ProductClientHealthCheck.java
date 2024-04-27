package com.cartItem.health;


import io.smallrye.health.checks.UrlHealthCheck;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class ProductClientHealthCheck {

    @ConfigProperty(name = "product-service/mp-rest/url")
    String productService;

    @Readiness
    HealthCheck checkUrl(){
        return  new UrlHealthCheck(productService+"/product/hello")
                .name("Product Service").requestMethod(HttpMethod.GET).statusCode(200);
    }


}
