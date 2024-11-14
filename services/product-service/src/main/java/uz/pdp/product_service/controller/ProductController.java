package uz.pdp.product_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.product_service.payload.base.ApiResult;
import uz.pdp.product_service.payload.product.req.ReqCreateProduct;
import uz.pdp.product_service.payload.product.req.ReqUpdateProduct;
import uz.pdp.product_service.payload.product.res.ResProduct;
import uz.pdp.product_service.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ApiResult<ResProduct> createProduct(@RequestBody ReqCreateProduct reqCreateProduct){
        return ApiResult.successResponse(productService.create(reqCreateProduct));
    }

    @PutMapping("/update")
    public ApiResult<ResProduct> updateProduct(@RequestBody ReqUpdateProduct reqUpdateProblem){
        return ApiResult.successResponse(productService.update(reqUpdateProblem));
    }

    @GetMapping("/get/{id}")
    public ApiResult<ResProduct> getProductById(@PathVariable Long id){
        return ApiResult.successResponse(productService.getProblemById(id));
    }

    @GetMapping("/getAll")
    public ApiResult<List<ResProduct>> getAllProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResult.successResponse(productService.getAll(page, size));
    }

    @DeleteMapping("/delete" + "/{id}")
    public ApiResult<String> deleteProductById(@PathVariable Long id){
        productService.delete(id);
        return ApiResult.successResponse("Product successfully deleted!");
    }


}
