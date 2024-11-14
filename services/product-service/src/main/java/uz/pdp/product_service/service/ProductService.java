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
import uz.pdp.product_service.mapper.ProductMapper;
import uz.pdp.product_service.payload.product.req.ReqCreateProduct;
import uz.pdp.product_service.payload.product.req.ReqUpdateProduct;
import uz.pdp.product_service.payload.product.res.ResProduct;
import uz.pdp.product_service.repository.CategoryRepository;
import uz.pdp.product_service.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ResProduct create(ReqCreateProduct reqCreateProduct) {
        if(productRepository.existsByName(reqCreateProduct.getName())) {
            throw RestException.restThrow(ErrorTypeEnum.PRODUCT_ALREADY_EXISTS);
        }

        Category category = categoryRepository.findCategoryById(reqCreateProduct.getCategoryId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));

        Product product = ProductMapper.fromReqToEntity(reqCreateProduct, category);
        productRepository.save(product);

        return ProductMapper.fromEntityToDto(product);
    }

    public ResProduct update(ReqUpdateProduct reqUpdateProduct) {
        Product product = productRepository.findById(reqUpdateProduct.getId())
                .orElseThrow(RestException.thew(ErrorTypeEnum.PRODUCT_NOT_FOUND));

        if (reqUpdateProduct.getName() != null && productRepository.existsByName(reqUpdateProduct.getName())) {
            throw RestException.restThrow(ErrorTypeEnum.PRODUCT_ALREADY_EXISTS);
        }

        Category category;

        if (reqUpdateProduct.getCategoryId() != null) {
            category = categoryRepository.findCategoryById(reqUpdateProduct.getCategoryId())
                    .orElseThrow(RestException.thew(ErrorTypeEnum.CATEGORY_NOT_FOUND));
        } else {
            category = categoryRepository.findCategoryById(product.getCategory().getId()).get();
        }

        ProductMapper.updateProduct(reqUpdateProduct, product, category);

        productRepository.save(product);

        return ProductMapper.fromEntityToDto(product);
    }

    public ResProduct getProblemById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PRODUCT_NOT_FOUND));

        return ProductMapper.fromEntityToDto(product);
    }

    public List<ResProduct> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Product> products = productRepository.findAll(pageRequest);

        return ProductMapper.pageToDtoList(products);
    }

    public Boolean delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(RestException.thew(ErrorTypeEnum.PRODUCT_NOT_FOUND));

        productRepository.delete(product);

        return true;
    }
}
