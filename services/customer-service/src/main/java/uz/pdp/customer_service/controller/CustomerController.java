package uz.pdp.customer_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.customer_service.payload.*;
import uz.pdp.customer_service.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/sign-up")
    public ApiResult<CustomerRes> register(@Valid @RequestBody CustomerAddReq req) {
        return ApiResult.successResponse(customerService.signUp(req));
    }

    @PostMapping("/sign-in")
    public ApiResult<CustomerRes> login(@Valid @RequestBody LoginReq req) {
        return ApiResult.successResponse(customerService.signIn(req));
    }

    @DeleteMapping("/delete/{email}")
    public ApiResult<String> deleteCustomer(@PathVariable String email) {
        return ApiResult.successResponse(customerService.delete(email));
    }

    @GetMapping("/get/{id}")
    public ApiResult<CustomerRes> getCustomer(@PathVariable String id) {
        return ApiResult.successResponse(customerService.getOne(id));
    }

    @PutMapping("/update")
    public ApiResult<CustomerRes> updateCustomer(@Valid @RequestBody CustomerUpdateReq req) {
        return ApiResult.successResponse(customerService.update(req));
    }

    @GetMapping("/get-all")
    public ApiResult<List<CustomerRes>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResult.successResponse(customerService.getAll(page, size));
    }

}
