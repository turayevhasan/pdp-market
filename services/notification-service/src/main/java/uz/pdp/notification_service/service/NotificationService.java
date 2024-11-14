package uz.pdp.notification_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pdp.notification_service.model.Notification;
import uz.pdp.notification_service.payload.NotificationDto;
import uz.pdp.notification_service.repository.NotificationRepository;

import java.util.Optional;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final MailSenderService mailSenderService;
    private final NotificationRepository notificationRepository;
    private final ObjectMapper jacksonObjectMapper;

    public NotificationService(MailSenderService mailSenderService, NotificationRepository notificationRepository, ObjectMapper jacksonObjectMapper) {
        this.mailSenderService = mailSenderService;
        this.notificationRepository = notificationRepository;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public void saveAndSendEmail(String data) {
        try {
            NotificationDto dto = jacksonObjectMapper.readValue(data, NotificationDto.class);

            Optional<Notification> found = notificationRepository.findByUniqueField(dto.getUniqueField());
            if (found.isPresent()) {
                log.error("Notification already sent {}", found.get());
                throw new RuntimeException("Notification already sent uniqueField=" + dto.getUniqueField());
            }

            Notification notification = Notification.builder()
                    .uniqueField(dto.getUniqueField())
                    .sender(dto.getSender())
                    .recipientEmail(dto.getRecipientEmail())
                    .message(dto.getMessage())
                    .build();


            try {
                mailSenderService.send(dto.getRecipientEmail(), dto.getSender(), dto.getMessage()); //sending confirmation to email
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            notification.setSent(true);
            notificationRepository.save(notification);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
