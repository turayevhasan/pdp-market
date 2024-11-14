package uz.pdp.payment_service.payload.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCreatePayment {
    @NotNull
    private Long orderId;

    @NotBlank
    private String customerId;

    @NotNull
    private Long amount;
}
