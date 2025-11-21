package in.rishabh_gupta.app.services;

import in.rishabh_gupta.app.dto.request.CategoryRequest;
import in.rishabh_gupta.app.dto.response.CategoryResponse;
import in.rishabh_gupta.app.enums.CategorySortFields;
import in.rishabh_gupta.app.enums.SortDirection;
import org.springframework.data.domain.Page;

public interface CategoryService {
    String NOT_FOUND_MESSAGE = "Category not found";
    String DUPLICATE_NAME_MESSAGE = "Category already exists";

    Page<CategoryResponse> index(String keyword, int pageNumber, int pageSize, CategorySortFields sortBy, SortDirection sortDir);

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse show(Long id);

    CategoryResponse update(Long id, CategoryRequest categoryRequest);

    CategoryResponse delete(Long id);

    CategoryResponse restore(Long id);
}
