package uz.pdp.order_service.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentReq {
    private Long orderId;
    private String customerId;
    private Long amount;
}
