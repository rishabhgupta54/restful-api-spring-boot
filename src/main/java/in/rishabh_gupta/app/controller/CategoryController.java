package in.rishabh_gupta.app.controller;

import in.rishabh_gupta.app.dto.ApiResponse;
import in.rishabh_gupta.app.dto.request.CategoryRequest;
import in.rishabh_gupta.app.dto.response.CategoryResponse;
import in.rishabh_gupta.app.enums.CategorySortFields;
import in.rishabh_gupta.app.enums.SortDirection;
import in.rishabh_gupta.app.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Operations related to categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "To get the list of all the categories")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> index(@RequestParam(required = false) String keyword, @RequestParam(required = false, defaultValue = "0") int pageNumber, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "id") CategorySortFields sortBy, @RequestParam(required = false, defaultValue = "ASC") SortDirection sortDir) {
        Page<CategoryResponse> categoryResponses = this.categoryService.index(keyword, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(categoryResponses.getContent(), categoryResponses));
    }

    @Operation(summary = "To create a new category")
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(ApiResponse.success(this.categoryService.create(categoryRequest)));
    }

    @Operation(summary = "To get the single category details")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> show(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(this.categoryService.show(id)));
    }

    @Operation(summary = "To update the single category details")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(ApiResponse.success(this.categoryService.update(id, categoryRequest)));
    }

    @Operation(summary = "To delete the single category details")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(this.categoryService.delete(id)));
    }

    @Operation(summary = "To restore the deleted category")
    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<CategoryResponse>> restore(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(this.categoryService.restore(id)));
    }
}
