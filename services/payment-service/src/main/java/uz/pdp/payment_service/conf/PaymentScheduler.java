package uz.pdp.payment_service.conf;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.pdp.payment_service.entity.PaymentStatus;
import uz.pdp.payment_service.repository.PaymentRepository;

@Component
@RequiredArgsConstructor
public class PaymentScheduler {
    private final PaymentRepository repository;

    @Scheduled(fixedRate = 86400000)  // works every 1 day
    public void clearPayment() {
        repository.findAll()
                .stream()
                .filter(payment -> payment.getStatus().equals(PaymentStatus.IN_PROGRESS))
                .forEach(repository::delete);
    }
}

