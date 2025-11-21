package in.rishabh_gupta.app.services;

import in.rishabh_gupta.app.dto.request.UserRequest;
import in.rishabh_gupta.app.dto.response.UserResponse;
import in.rishabh_gupta.app.enums.SortDirection;
import in.rishabh_gupta.app.enums.UserSortFields;
import org.springframework.data.domain.Page;

public interface UserService {
    String NOT_FOUND_MESSAGE = "User not found";
    String DUPLICATE_EMAIL_MESSAGE = "Email already taken";

    Page<UserResponse> index(String keyword, int pageNumber, int pageSize, UserSortFields sortBy, SortDirection sortDir);

    UserResponse create(UserRequest userRequest);

    UserResponse show(Long id);

    UserResponse update(Long id, UserRequest userRequest);

    UserResponse delete(Long id);

    UserResponse restore(Long id);
}
