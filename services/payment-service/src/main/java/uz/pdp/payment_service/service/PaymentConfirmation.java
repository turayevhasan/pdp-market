package uz.pdp.payment_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import uz.pdp.payment_service.entity.Payment;
import uz.pdp.payment_service.payload.dto.NotificationDto;

import java.time.format.DateTimeFormatter;

@Service
@EnableAsync
@RequiredArgsConstructor
public class PaymentConfirmation {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Async
    public void sendPaymentConfirmation(Payment payment, String customerEmail) {
        String check =
                "Your purchase successfully paid!. Amount: " +
                        payment.getAmount() +
                        ", Time: " +
                        payment.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));


        NotificationDto notification = new NotificationDto(
                "P" + payment.getId(),
                "Payment-Service",
                customerEmail,
                check
        );
        try {
            String json = objectMapper.writeValueAsString(notification);
            kafkaTemplate.send("payment_confirmation", json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize notification", e);
        }
    }
}
