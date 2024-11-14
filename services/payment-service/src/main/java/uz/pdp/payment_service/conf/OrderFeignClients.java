package uz.pdp.payment_service.conf;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.payment_service.payload.dto.OrderDto;
import uz.pdp.payment_service.payload.res.DataFromRes;

@Component
@FeignClient(name = "OrderService", url = "http://localhost:8080")
public interface OrderFeignClients {
    @GetMapping("/order/get/{id}")
    DataFromRes<OrderDto> getOrderById(@PathVariable("id") Long id);
}

