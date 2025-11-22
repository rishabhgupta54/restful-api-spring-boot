package in.rishabh_gupta.app.services.impl;

import in.rishabh_gupta.app.dto.request.CategoryRequest;
import in.rishabh_gupta.app.dto.response.CategoryResponse;
import in.rishabh_gupta.app.entity.Category;
import in.rishabh_gupta.app.enums.CategorySortFields;
import in.rishabh_gupta.app.enums.SortDirection;
import in.rishabh_gupta.app.exception.DuplicateCategoryException;
import in.rishabh_gupta.app.exception.DuplicateEmailException;
import in.rishabh_gupta.app.exception.ResourceNotFoundException;
import in.rishabh_gupta.app.mapper.CategoryMapper;
import in.rishabh_gupta.app.repository.CategoryRepository;
import in.rishabh_gupta.app.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponse> index(String keyword, int pageNumber, int pageSize, CategorySortFields sortBy, SortDirection sortDir) {
        Sort sort = sortDir.equals(SortDirection.ASC) ? Sort.by(sortBy.name()).ascending() : Sort.by(sortBy.name()).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> categories = null;

        if (keyword == null || keyword.trim().isEmpty()) {
            categories = this.categoryRepository.findAll(pageable);
        } else {
            categories = this.categoryRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }

        return categories.map(this.categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        Category checkExistingCategory = this.categoryRepository.findByName(categoryRequest.getName());
        if (checkExistingCategory != null) {
            throw new DuplicateCategoryException(DUPLICATE_NAME_MESSAGE);
        }

        Category category = this.categoryMapper.toEntity(categoryRequest);
        Category savedCategory = this.categoryRepository.save(category);

        return this.categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse show(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
        return this.categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
        Category checkExistingCategory = this.categoryRepository.findByName(categoryRequest.getName());
        if (checkExistingCategory != null && !checkExistingCategory.getId().equals(category.getId())) {
            throw new DuplicateEmailException(DUPLICATE_NAME_MESSAGE);
        }
        category.setName(categoryRequest.getName());
        category = this.categoryRepository.save(category);

        return this.categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse delete(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_MESSAGE));
        category.setDeletedAt(LocalDateTime.now());
        this.categoryRepository.save(category);

        return this.categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse restore(Long id) {
        Category category = this.categoryRepository.findByIdAndDeletedAtIsNotNull(id);
        category.setDeletedAt(null);
        return this.categoryMapper.toResponse(this.categoryRepository.save(category));
    }
}
