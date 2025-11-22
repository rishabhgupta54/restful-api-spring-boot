package in.rishabh_gupta.app.controller;

import in.rishabh_gupta.app.dto.ApiResponse;
import in.rishabh_gupta.app.dto.response.BlogResponse;
import in.rishabh_gupta.app.enums.BlogSortFields;
import in.rishabh_gupta.app.enums.SortDirection;
import in.rishabh_gupta.app.services.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Blogs", description = "Operation related to blogs")
@RequiredArgsConstructor
public class BlogController {
    protected final BlogService blogService;

    @Operation(summary = "To get the list of all the blogs")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BlogResponse>>> index(@RequestParam(required = false) String keyword, @RequestParam(required = false, defaultValue = "0") int pageNumber, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "id") BlogSortFields sortBy, @RequestParam(required = false, defaultValue = "ASC") SortDirection sortDir) {
        Page<BlogResponse> blogResponses = this.blogService.index(keyword, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(blogResponses.getContent(), blogResponses));
    }
}
