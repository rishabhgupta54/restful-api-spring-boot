package in.rishabh_gupta.app.controller;

import in.rishabh_gupta.app.dto.ApiResponse;
import in.rishabh_gupta.app.dto.request.UserRequest;
import in.rishabh_gupta.app.dto.response.UserResponse;
import in.rishabh_gupta.app.entity.AppUser;
import in.rishabh_gupta.app.enums.SortDirection;
import in.rishabh_gupta.app.enums.UserSortFields;
import in.rishabh_gupta.app.services.UserService;
import in.rishabh_gupta.app.validation_groups.UserCreate;
import in.rishabh_gupta.app.validation_groups.UserUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operations related to users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "To get the list of all the users")
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> index(@RequestParam(required = false) String keyword, @RequestParam(required = false, defaultValue = "0") int pageNumber, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "id") UserSortFields sortBy, @RequestParam(required = false, defaultValue = "ASC") SortDirection sortDir) {
        Page<UserResponse> userResponses = this.userService.index(keyword, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(ApiResponse.success(userResponses.getContent(), userResponses));
    }

    @Operation(summary = "To create a new user")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Validated(UserCreate.class) @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(ApiResponse.success(this.userService.create(userRequest)));
    }

    @Operation(summary = "To get the single user details")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> show(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(this.userService.show(id)));
    }

    @Operation(summary = "To update the single user details")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable Long id, @Validated(UserUpdate.class) @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(ApiResponse.success(this.userService.update(id, userRequest)));
    }

    @Operation(summary = "To delete the single user details")
    @PatchMapping("/{id}/delete")
    public ResponseEntity<ApiResponse<UserResponse>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(this.userService.delete(id)));
    }

    @Operation(summary = "To restore the deleted user")
    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<UserResponse>> restore(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(this.userService.restore(id)));
    }
}
