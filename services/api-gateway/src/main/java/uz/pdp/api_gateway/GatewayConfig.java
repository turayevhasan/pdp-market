package uz.pdp.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("CustomerService", r -> r.path("/customer/**")
                        .uri("lb://CustomerService"))

                .route("OrderService", r -> r.path("/order/**")
                        .uri("lb://OrderService"))

                .route("PaymentService", r -> r.path("/payment/**")
                        .uri("lb://PaymentService"))

                .route("ProductService", r -> r.path("/product/**")
                        .uri("lb://ProductService"))

                .route("ProductService", r -> r.path("/category/**")
                        .uri("lb://ProductService"))

                .build();
    }
}


