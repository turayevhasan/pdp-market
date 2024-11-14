package uz.pdp.notification_service.conf;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import uz.pdp.notification_service.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);
    private final NotificationService notificationService;

    @KafkaListener(topics = "payment_confirmation", groupId = "groupId")
    void paymentListener(String data) {
        log.info("PAYMENT LISTENER RECEIVED {}", data);
        notificationService.saveAndSendEmail(data);
    }

    @KafkaListener(topics = "order_confirmation", groupId = "groupId")
    void orderListener(String data) {
        log.info("ORDER LISTENER RECEIVED {}", data);
        notificationService.saveAndSendEmail(data);
    }
}
