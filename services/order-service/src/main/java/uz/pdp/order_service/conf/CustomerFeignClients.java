package uz.pdp.order_service.conf;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.pdp.order_service.payload.dto.CustomerDto;
import uz.pdp.order_service.payload.res.DataFromRes;

@FeignClient(name = "CustomerService", url = "http://localhost:8080")
public interface CustomerFeignClients {
    @GetMapping("/customer/get/{id}")
    DataFromRes<CustomerDto> getCustomerById(@PathVariable("id") String id);
}

