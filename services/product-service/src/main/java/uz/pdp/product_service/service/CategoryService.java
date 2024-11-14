package uz.pdp.product_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pdp.product_service.entity.Category;
import uz.pdp.product_service.entity.Product;
import uz.pdp.product_service.enums.ErrorTypeEnum;
import uz.pdp.product_service.exceptions.RestException;
import uz.pdp.product_service.mapper.CategoryMapper;
import uz.pdp.product_service.payload.category.req.ReqCreateCategory;
import uz.pdp.product_service.payload.category.req.ReqUpdateCategory;
import uz.pdp.product_service.payload.category.res.ResCategory;
import uz.pdp.product_service.repository.CategoryRepository;
import uz.pdp.product_service.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ResCategory create(ReqCreateCategory reqCreateCategory) {
        if(categoryRepository.existsCategoryByName(reqCreateCategory.getName())) {
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_ALREADY_EXISTS);
        }

        Category category = CategoryMapper.fromReqToEntity(reqCreateCategory);

        categoryRepository.save(category);

        return CategoryMapper.fromEntityToDto(category);
    }

    public ResCategory update(ReqUpdateCategory reqUpdateCategory) {
        Category category = categoryRepository.findCategoryById(reqUpdateCategory.getId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        if(reqUpdateCategory.getName() != null && categoryRepository.existsCategoryByName(reqUpdateCategory.getName())) {
            throw RestException.restThrow(ErrorTypeEnum.CATEGORY_ALREADY_EXISTS);
        }

        CategoryMapper.updateCategory(reqUpdateCategory, category);

        categoryRepository.save(category);

        return CategoryMapper.fromEntityToDto(category);
    }

    public List<ResCategory> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Category> categories = categoryRepository.findAll(pageRequest);

        return CategoryMapper.pageToDtoList(categories);
    }

    public ResCategory getCategoryById(Long id) {
        Category category = categoryRepository.findCategoryById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        return CategoryMapper.fromEntityToDto(category);
    }

    public Boolean delete(Long id) {
        Category category = categoryRepository.findCategoryById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        deleteProductsByCategoryId(id);
        categoryRepository.delete(category);

        return true;
    }

    public void deleteProductsByCategoryId(Long id) {
        List<Product> products = productRepository.findAllByCategoryId(id);

        productRepository.deleteAll(products);
    }


}
