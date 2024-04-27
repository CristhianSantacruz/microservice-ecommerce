package com.product.healt;

import com.product.service.ProductService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class LivenessHealthCheck implements HealthCheck {

    @Inject
    ProductService productService;


    @Override
    public HealthCheckResponse call() {
        productService.getAllProducts();
        return HealthCheckResponse.named("All Products").up().build();

    }
}
