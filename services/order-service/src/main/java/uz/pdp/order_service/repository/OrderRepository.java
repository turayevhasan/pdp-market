package uz.pdp.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.order_service.entity.Order;
import uz.pdp.order_service.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByCustomerIdAndStatus(String customerId, OrderStatus status);
    List<Order> findAllByCustomerId(String customerId);
}
