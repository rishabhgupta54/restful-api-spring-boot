package in.rishabh_gupta.app.services;

import in.rishabh_gupta.app.dto.response.CategoryResponse;

import java.util.List;

public interface UserBlogCategoryService {
    List<CategoryResponse> index(Long userId, Long blogId);
    CategoryResponse add(Long userId, Long blogId, Long categoryId);
    CategoryResponse remove(Long userId, Long blogId, Long categoryId);
}