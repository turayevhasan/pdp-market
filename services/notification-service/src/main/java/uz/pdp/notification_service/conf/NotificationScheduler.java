package uz.pdp.notification_service.conf;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.notification_service.model.Notification;
import uz.pdp.notification_service.repository.NotificationRepository;
import uz.pdp.notification_service.service.MailSenderService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {
    private final NotificationRepository repository;
    private final MailSenderService mailSenderService;

    @Scheduled(fixedRate = 30 * 60 * 1000)  // works every 30 minutes
    public void check() {
        List<Notification> all = repository.findAll();

        for (Notification notification : all) {
            if (!notification.isSent()) {
                try {
                    mailSenderService.send(notification.getRecipientEmail(), notification.getSender(), notification.getMessage());
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
                notification.setSent(true);
                repository.save(notification);
            }
        }
    }


}
