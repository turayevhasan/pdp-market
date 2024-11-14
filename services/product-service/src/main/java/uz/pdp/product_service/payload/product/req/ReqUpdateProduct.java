package uz.pdp.product_service.payload.product.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUpdateProduct {
    @NotNull
    private Long id;

    private String name;

    private String description;

    private Long availableQuantity;

    private Long price;

    private Long categoryId;
}
