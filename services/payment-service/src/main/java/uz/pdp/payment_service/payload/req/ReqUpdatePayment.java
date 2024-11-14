package uz.pdp.payment_service.payload.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.pdp.payment_service.entity.PaymentStatus;

@Getter
@Setter
public class ReqUpdatePayment {
    @NotBlank
    private String customerId;

    private PaymentStatus status;
}
