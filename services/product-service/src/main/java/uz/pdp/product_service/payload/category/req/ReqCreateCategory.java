package uz.pdp.product_service.payload.category.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreateCategory {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
