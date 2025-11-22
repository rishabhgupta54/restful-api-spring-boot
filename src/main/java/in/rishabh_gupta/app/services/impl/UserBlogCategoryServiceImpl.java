package in.rishabh_gupta.app.services.impl;

import in.rishabh_gupta.app.dto.response.BlogResponse;
import in.rishabh_gupta.app.dto.response.CategoryResponse;
import in.rishabh_gupta.app.entity.AppUser;
import in.rishabh_gupta.app.entity.Blog;
import in.rishabh_gupta.app.entity.Category;
import in.rishabh_gupta.app.exception.ResourceNotFoundException;
import in.rishabh_gupta.app.mapper.CategoryMapper;
import in.rishabh_gupta.app.repository.BlogRepository;
import in.rishabh_gupta.app.repository.CategoryRepository;
import in.rishabh_gupta.app.repository.UserRepository;
import in.rishabh_gupta.app.services.BlogService;
import in.rishabh_gupta.app.services.CategoryService;
import in.rishabh_gupta.app.services.UserBlogCategoryService;
import in.rishabh_gupta.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBlogCategoryServiceImpl implements UserBlogCategoryService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> index(Long userId, Long blogId) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException(UserService.NOT_FOUND_MESSAGE));
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new RuntimeException(BlogService.NOT_FOUND_MESSAGE));

        return blog.getCategories().stream().map(this.categoryMapper::toResponse).toList();
    }

    @Override
    public CategoryResponse add(Long userId, Long blogId, Long categoryId) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException(UserService.NOT_FOUND_MESSAGE));
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new RuntimeException(BlogService.NOT_FOUND_MESSAGE));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(CategoryService.NOT_FOUND_MESSAGE));

        if (!blog.getCategories().contains(category)) {
            blog.getCategories().add(category);
            this.blogRepository.save(blog);
        }

        return this.categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse remove(Long userId, Long blogId, Long categoryId) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new RuntimeException(UserService.NOT_FOUND_MESSAGE));
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new RuntimeException(BlogService.NOT_FOUND_MESSAGE));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(CategoryService.NOT_FOUND_MESSAGE));
        if (blog.getCategories().contains(category)) {
            blog.getCategories().remove(category);
            this.blogRepository.save(blog);
        }

        return this.categoryMapper.toResponse(category);
    }
}
