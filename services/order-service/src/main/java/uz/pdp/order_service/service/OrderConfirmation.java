package uz.pdp.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import uz.pdp.order_service.conf.CustomerFeignClients;
import uz.pdp.order_service.conf.PaymentFeignClients;
import uz.pdp.order_service.conf.ProductFeignClients;
import uz.pdp.order_service.entity.Order;
import uz.pdp.order_service.entity.OrderLine;
import uz.pdp.order_service.payload.dto.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@EnableAsync
public class OrderConfirmation {
    private static final Logger log = LoggerFactory.getLogger(OrderConfirmation.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final CustomerFeignClients customerFeignClients;
    private final ProductFeignClients productFeignClients;
    private final PaymentFeignClients paymentFeignClients;

    @Async
    public void sendOrderConfirmation(Order order, boolean confirmed) {
        CustomerDto customer = customerFeignClients.getCustomerById(order.getCustomerId()).getData();
        List<OrderLine> lines = order.getOrderLines();

        StringBuilder check = new StringBuilder();
        if (confirmed) {
            check.append("Your orders are confirmed. ");
        } else {
            check.append("Your orders are declined. ");
        }
        check.append("Products :");

        long amount = 0L;

        for (OrderLine line : lines) {
            ProductDto product = productFeignClients.getProductById(line.getProductId()).getData();
            check
                    .append(product.getName())
                    .append(" * ")
                    .append(line.getQuantity())
                    .append("x = ")
                    .append(product.getPrice() * line.getQuantity())
                    .append(", ");

            amount += product.getPrice() * line.getQuantity();
        }

        check.append("Amount :").append(amount);
        check.append(", Time :").append(order.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));


        NotificationDto notification = new NotificationDto(
                "O" + order.getId(),
                "Order-Service",
                customer.getEmail(),
                check.toString()
        );
        try {
            String json = objectMapper.writeValueAsString(notification);
            kafkaTemplate.send("order_confirmation", json); //sent it to notification_service
            log.info("Order confirmation sent");

            if (confirmed) {
                PaymentDto payment = paymentFeignClients.sendPayment(new PaymentReq(
                        order.getId(),
                        customer.getId(),
                        amount)
                ).getData(); //sent add payment request

                log.info("Send create payment request {}", payment);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize notification", e);
        }
    }

}

