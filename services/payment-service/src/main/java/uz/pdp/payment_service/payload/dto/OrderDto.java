package uz.pdp.payment_service.payload.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;

    private String customerId;

    private String status;
}
