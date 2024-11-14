package uz.pdp.order_service.payload.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderLinesDto {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
