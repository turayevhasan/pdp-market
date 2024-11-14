package uz.pdp.payment_service.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private String uniqueField;
    private String sender;
    private String recipientEmail;
    private String message;
}
