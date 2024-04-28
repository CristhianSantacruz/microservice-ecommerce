package com.cart.health;

import io.smallrye.health.checks.UrlHealthCheck;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.HttpMethod;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Readiness;

import java.util.Objects;

@ApplicationScoped
public class CartItemClientHealthCheck {

    @ConfigProperty(name = "cartItem-service/mp-rest/url")
    String cartItemServiceUrl;

    @Readiness
    HealthCheck checkUrl(){
        return Objects.requireNonNull(new UrlHealthCheck(cartItemServiceUrl + "/cart-item/hello")
                .name("Cart Item Service ChecK")
                .requestMethod(HttpMethod.GET)
                .statusCode(200));
    }
}
