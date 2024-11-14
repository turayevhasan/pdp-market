package uz.pdp.product_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.product_service.payload.base.ApiResult;
import uz.pdp.product_service.payload.category.req.ReqCreateCategory;
import uz.pdp.product_service.payload.category.req.ReqUpdateCategory;
import uz.pdp.product_service.payload.category.res.ResCategory;
import uz.pdp.product_service.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public ApiResult<ResCategory> createCategory(@Valid @RequestBody ReqCreateCategory reqCreateCategory){
        return ApiResult.successResponse(categoryService.create(reqCreateCategory));
    }

    @PutMapping("/update")
    public ApiResult<ResCategory> updateCategory(@Valid @RequestBody ReqUpdateCategory reqUpdateCategory){
        return ApiResult.successResponse(categoryService.update(reqUpdateCategory));
    }

    @GetMapping("/getAll")
    public ApiResult<List<ResCategory>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResult.successResponse(categoryService.getAll(page, size));
    }

    @GetMapping("/get/{id}")
    public ApiResult<ResCategory> getCategoryById(@PathVariable Long id){
        return ApiResult.successResponse(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<String> deleteCategoryById(@PathVariable Long id){
        categoryService.delete(id);
        return ApiResult.successResponse("Successfully deleted");
    }
}
