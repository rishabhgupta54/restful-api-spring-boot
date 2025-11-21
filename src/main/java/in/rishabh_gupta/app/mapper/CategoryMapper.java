package in.rishabh_gupta.app.mapper;

import in.rishabh_gupta.app.dto.request.CategoryRequest;
import in.rishabh_gupta.app.dto.response.CategoryResponse;
import in.rishabh_gupta.app.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());

        return category;
    }

    public CategoryResponse toResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setCreatedAt(category.getCreatedAt());
        categoryResponse.setUpdatedAt(category.getUpdatedAt());
        return categoryResponse;
    }
}
