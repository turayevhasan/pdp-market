package uz.pdp.order_service.conf;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.order_service.payload.req.ProductUpdateReq;
import uz.pdp.order_service.payload.res.DataFromRes;
import uz.pdp.order_service.payload.dto.ProductDto;

@FeignClient(name = "ProductService", url = "http://localhost:8080")
public interface ProductFeignClients {
    @GetMapping("/product/get/{id}")
    DataFromRes<ProductDto> getProductById(@PathVariable("id") Long id);

    @PutMapping("/product/update")
    DataFromRes<ProductDto> updateProduct(@RequestBody ProductUpdateReq req);
}

