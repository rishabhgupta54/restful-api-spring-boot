package in.rishabh_gupta.app.services;

import in.rishabh_gupta.app.dto.response.UserResponse;

public interface BlogUserService {
    UserResponse index(Long blogId);
}
