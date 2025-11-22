package in.rishabh_gupta.app.services.impl;

import in.rishabh_gupta.app.dto.response.UserResponse;
import in.rishabh_gupta.app.entity.Blog;
import in.rishabh_gupta.app.exception.ResourceNotFoundException;
import in.rishabh_gupta.app.mapper.UserMapper;
import in.rishabh_gupta.app.repository.BlogRepository;
import in.rishabh_gupta.app.services.BlogService;
import in.rishabh_gupta.app.services.BlogUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogUserServiceImpl implements BlogUserService {

    protected final BlogRepository blogRepository;
    protected final UserMapper userMapper;

    @Override
    public UserResponse index(Long blogId) {
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException(BlogService.NOT_FOUND_MESSAGE));
        return this.userMapper.toResponse(blog.getUser());
    }
}
