package uz.pdp.product_service.mapper;

import org.springframework.data.domain.Page;
import uz.pdp.product_service.entity.Category;
import uz.pdp.product_service.payload.category.req.ReqCreateCategory;
import uz.pdp.product_service.payload.category.req.ReqUpdateCategory;
import uz.pdp.product_service.payload.category.res.ResCategory;

import java.util.ArrayList;
import java.util.List;

import static uz.pdp.product_service.utils.CoreUtils.getIfExists;

public interface CategoryMapper {
    static ResCategory fromEntityToDto(Category category) {
        return ResCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    static Category fromReqToEntity(ReqCreateCategory reqCreateCategory) {
        return Category.builder()
                .name(reqCreateCategory.getName())
                .description(reqCreateCategory.getDescription())
                .build();
    }

    static void updateCategory(ReqUpdateCategory reqUpdateCategory, Category category) {
        category.setName(getIfExists(reqUpdateCategory.getName(), category.getName()));
        category.setDescription(getIfExists(reqUpdateCategory.getDescription(), category.getDescription()));
    }

    static List<ResCategory> pageToDtoList(Page<Category> categories) {
        ArrayList<ResCategory> resCategories = new ArrayList<>();

        categories.forEach(category ->
                resCategories.add(fromEntityToDto(category))
        );

        return resCategories;
    }

}
