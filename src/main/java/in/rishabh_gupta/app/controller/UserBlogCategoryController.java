package in.rishabh_gupta.app.controller;

import in.rishabh_gupta.app.dto.ApiResponse;
import in.rishabh_gupta.app.dto.response.CategoryResponse;
import in.rishabh_gupta.app.services.UserBlogCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Blog's Categories", description = "To manage categories of the users's blog")
@RequestMapping("/api/users/{userId}/blogs/{blogId}/categories")
public class UserBlogCategoryController {

    private final UserBlogCategoryService userBlogCategoryService;

    @Operation(summary = "To get the list of all the categories")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> index(@PathVariable Long userId, @PathVariable Long blogId) {
        return ResponseEntity.ok(ApiResponse.success(this.userBlogCategoryService.index(userId, blogId)));
    }

    @Operation(summary = "To add the category to the blog")
    @PostMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> add(@PathVariable Long userId, @PathVariable Long blogId, @PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.success(this.userBlogCategoryService.add(userId, blogId, categoryId)));
    }

    @Operation(summary = "To remove the category to the blog")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> remove(@PathVariable Long userId, @PathVariable Long blogId, @PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.success(this.userBlogCategoryService.remove(userId, blogId, categoryId)));
    }
}
