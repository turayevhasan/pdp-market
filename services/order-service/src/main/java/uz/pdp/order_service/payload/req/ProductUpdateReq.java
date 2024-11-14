package uz.pdp.order_service.payload.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductUpdateReq {
    @NotNull
    private Long id;

    private String name;

    private String description;

    private Long availableQuantity;

    private Long price;

    private Long categoryId;

    public ProductUpdateReq(Long id, Long availableQuantity){
        this.id = id;
        this.availableQuantity = availableQuantity;
    }
}
