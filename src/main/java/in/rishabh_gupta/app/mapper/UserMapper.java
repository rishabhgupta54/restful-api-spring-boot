package in.rishabh_gupta.app.mapper;

import in.rishabh_gupta.app.dto.request.UserRequest;
import in.rishabh_gupta.app.dto.response.UserResponse;
import in.rishabh_gupta.app.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public AppUser toEntity(UserRequest userRequest) {
        AppUser user = new AppUser();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        return user;
    }

    public UserResponse toResponse(AppUser user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }
}
