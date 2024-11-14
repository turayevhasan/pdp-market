package uz.pdp.order_service.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.order_service.payload.base.ApiResult;
import uz.pdp.order_service.payload.req.OrderAddReq;
import uz.pdp.order_service.payload.req.OrderUpdateReq;
import uz.pdp.order_service.payload.res.OrderRes;
import uz.pdp.order_service.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add")
    public ApiResult<OrderRes> addOrder(@RequestBody @Valid OrderAddReq req) {
        return ApiResult.successResponse(orderService.add(req));
    }

    @PutMapping("/update")
    public ApiResult<OrderRes> updateOrder(@RequestBody @Valid OrderUpdateReq req) {
        return ApiResult.successResponse(orderService.update(req));
    }

    @GetMapping("/get/{id}")
    public ApiResult<OrderRes> getOrder(@PathVariable long id) {
        return ApiResult.successResponse(orderService.getOne(id));
    }

    @GetMapping("/get-by-customer/{id}")
    public ApiResult<List<OrderRes>> getOrdersByCustomerId(@NotNull @PathVariable String id) {
        return ApiResult.successResponse(orderService.getAllByCustomer(id));
    }
}
