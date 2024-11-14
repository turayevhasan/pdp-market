package uz.pdp.payment_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.payment_service.payload.base.ApiResult;
import uz.pdp.payment_service.payload.req.ReqCreatePayment;
import uz.pdp.payment_service.payload.req.ReqUpdatePayment;
import uz.pdp.payment_service.payload.res.ResPayment;
import uz.pdp.payment_service.service.PaymentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create")
    public ApiResult<ResPayment> createPayment(@Valid @RequestBody ReqCreatePayment reqCreatePayment) {
        return ApiResult.successResponse(paymentService.create(reqCreatePayment));
    }

    @PutMapping("/update")
    public ApiResult<ResPayment> updatePayment(@Valid @RequestBody ReqUpdatePayment reqUpdatePayment) {
        return ApiResult.successResponse(paymentService.update(reqUpdatePayment));
    }

    @GetMapping("/get/{id}")
    public ApiResult<ResPayment> getPaymentById(@PathVariable Long id) {
        return ApiResult.successResponse(paymentService.getPaymentById(id));
    }

    @GetMapping("/get-by-customer/{id}")
    public ApiResult<List<ResPayment>> getPaymentsByCustomerId(@PathVariable String id) {
        return ApiResult.successResponse(paymentService.getAllByCustomerId(id));
    }

    @GetMapping("/get-all")
    public ApiResult<List<ResPayment>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResult.successResponse(paymentService.getAll(page, size));
    }
}
