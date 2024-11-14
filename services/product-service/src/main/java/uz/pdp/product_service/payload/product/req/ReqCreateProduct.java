package uz.pdp.product_service.payload.product.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreateProduct {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long availableQuantity;

    @NotNull
    private Long price;

    @NotNull
    private Long categoryId;
}
