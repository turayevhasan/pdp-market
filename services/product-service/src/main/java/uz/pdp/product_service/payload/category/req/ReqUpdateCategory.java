package uz.pdp.product_service.payload.category.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqUpdateCategory {
    @NotNull
    private Long id;

    private String name;

    private String description;
}
