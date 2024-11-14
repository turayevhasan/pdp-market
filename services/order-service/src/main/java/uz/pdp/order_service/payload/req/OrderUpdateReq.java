package uz.pdp.order_service.payload.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.order_service.enums.OrderStatus;

@Data
public class OrderUpdateReq {
    @NotNull
    private Long orderId;
    private OrderStatus status;
}
