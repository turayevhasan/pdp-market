package uz.pdp.order_service.conf;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.order_service.payload.dto.PaymentDto;
import uz.pdp.order_service.payload.dto.PaymentReq;
import uz.pdp.order_service.payload.res.DataFromRes;

@FeignClient(name = "PaymentService", url = "http://localhost:8080")
public interface PaymentFeignClients {
    @PostMapping("/payment/create")
    DataFromRes<PaymentDto> sendPayment(@RequestBody PaymentReq req);
}
