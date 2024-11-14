package uz.pdp.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.payment_service.entity.Payment;
import uz.pdp.payment_service.entity.PaymentStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByCustomerIdAndStatus(String customerId, PaymentStatus status);

    List<Payment> findAllByCustomerId(String orderId);

    boolean existsByOrderIdAndStatus(Long orderId, PaymentStatus status);

}
