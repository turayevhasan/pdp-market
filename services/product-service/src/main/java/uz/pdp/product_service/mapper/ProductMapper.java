package uz.pdp.product_service.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.product_service.entity.Category;
import uz.pdp.product_service.entity.Product;
import uz.pdp.product_service.payload.product.req.ReqCreateProduct;
import uz.pdp.product_service.payload.product.req.ReqUpdateProduct;
import uz.pdp.product_service.payload.product.res.ResProduct;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.product_service.utils.CoreUtils.getIfExists;

public interface ProductMapper {
    static ResProduct fromEntityToDto(Product product) {
        return ResProduct.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .availableQuantity(product.getAvailableQuantity())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    static Product fromReqToEntity(ReqCreateProduct reqCreateProduct, Category category){


        return Product.builder()
                .name(reqCreateProduct.getName())
                .description(reqCreateProduct.getDescription())
                .availableQuantity(reqCreateProduct.getAvailableQuantity())
                .price(reqCreateProduct.getPrice())
                .category(category)
                .build();
    }

    static void updateProduct(ReqUpdateProduct reqUpdateProduct, Product product, Category category){
        product.setName(getIfExists(reqUpdateProduct.getName(), product.getName()));
        product.setDescription(getIfExists(reqUpdateProduct.getDescription(), product.getDescription()));
        product.setAvailableQuantity(getIfExists(reqUpdateProduct.getAvailableQuantity(), product.getAvailableQuantity()));
        product.setPrice(getIfExists(reqUpdateProduct.getPrice(), product.getPrice()));
        product.setCategory(category);
    }

    static List<ResProduct> pageToDtoList(Page<Product> products){
        List<ResProduct> resProducts = new ArrayList<>();

        products.forEach(product ->
                resProducts.add(fromEntityToDto(product))
        );

        return resProducts;
    }
}
