package uz.pdp.notification_service.payload;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationDto implements Serializable {
    private String uniqueField;

    private String sender;

    private String recipientEmail;

    private String message;
}
