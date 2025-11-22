package in.rishabh_gupta.app.controller;

import in.rishabh_gupta.app.dto.ApiResponse;
import in.rishabh_gupta.app.dto.request.BlogRequest;
import in.rishabh_gupta.app.dto.response.BlogResponse;
import in.rishabh_gupta.app.enums.BlogSortFields;
import in.rishabh_gupta.app.enums.SortDirection;
import in.rishabh_gupta.app.services.BlogService;
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
@RequestMapping("/api/users/{userId}/blogs")
@Tag(name = "User's Blogs", description = "Operations related to User's blogs")
public class UserBlogController {

    private final BlogService blogService;

    @Operation(summary = "To get the list of all the blogs of a specified user")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BlogResponse>>> index(@PathVariable Long userId, @RequestParam(required = false) String keyword, @RequestParam(required = false, defaultValue = "0") int pageNumber, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "id") BlogSortFields sortBy, @RequestParam(required = false, defaultValue = "ASC") SortDirection sortDir) {
        Page<BlogResponse> blogResponses = this.blogService.index(userId, keyword, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(blogResponses.getContent()));
    }

    @Operation(summary = "To create a new blog of a specified user")
    @PostMapping
    public ResponseEntity<ApiResponse<BlogResponse>> create(@PathVariable Long userId, @Valid @RequestBody BlogRequest blogRequest) {
        return ResponseEntity.ok(ApiResponse.success(this.blogService.create(userId, blogRequest)));
    }

    @Operation(summary = "To get the single blog of a specified user")
    @GetMapping("/{blogId}")
    public ResponseEntity<ApiResponse<BlogResponse>> show(@PathVariable Long userId, @PathVariable Long blogId) {
        return ResponseEntity.ok(ApiResponse.success(this.blogService.show(userId, blogId)));
    }

    @Operation(summary = "To update the single blog of a specified user")
    @PatchMapping("/{blogId}")
    public ResponseEntity<ApiResponse<BlogResponse>> update(@PathVariable Long userId, @PathVariable Long blogId, @Valid @RequestBody BlogRequest blogRequest) {
        return ResponseEntity.ok(ApiResponse.success(this.blogService.update(userId, blogId, blogRequest)));
    }

    @Operation(summary = "To delete the single blog of a specified user")
    @DeleteMapping("/{blogId}")
    public ResponseEntity<ApiResponse<BlogResponse>> delete(@PathVariable Long userId, @PathVariable Long blogId) {
        return ResponseEntity.ok(ApiResponse.success(this.blogService.delete(userId, blogId)));
    }

    @Operation(summary = "To restore the deleted blog of a specified user")
    @PatchMapping("/{blogId}/restore")
    public ResponseEntity<ApiResponse<BlogResponse>> restore(@PathVariable Long userId, @PathVariable Long blogId) {
        return ResponseEntity.ok(ApiResponse.success(this.blogService.restore(userId, blogId)));
    }
}
