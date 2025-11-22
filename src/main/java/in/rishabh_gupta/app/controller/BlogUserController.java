package in.rishabh_gupta.app.controller;

import in.rishabh_gupta.app.dto.ApiResponse;
import in.rishabh_gupta.app.dto.response.UserResponse;
import in.rishabh_gupta.app.services.BlogUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/blogs/{blogId}")
@Tag(name = "Blog's User", description = "Operation related to Blog and user")
public class BlogUserController {

    private final BlogUserService blogUserService;

    @Operation(summary = "To get the user of the blog")
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<UserResponse>> index(@PathVariable Long blogId) {
        return ResponseEntity.ok(ApiResponse.success(this.blogUserService.index(blogId)));
    }
}
