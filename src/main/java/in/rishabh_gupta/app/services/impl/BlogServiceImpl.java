package in.rishabh_gupta.app.services.impl;

import in.rishabh_gupta.app.dto.request.BlogRequest;
import in.rishabh_gupta.app.dto.request.UserRequest;
import in.rishabh_gupta.app.dto.response.BlogResponse;
import in.rishabh_gupta.app.entity.AppUser;
import in.rishabh_gupta.app.entity.Blog;
import in.rishabh_gupta.app.enums.BlogSortFields;
import in.rishabh_gupta.app.enums.SortDirection;
import in.rishabh_gupta.app.exception.ResourceNotFoundException;
import in.rishabh_gupta.app.mapper.BlogMapper;
import in.rishabh_gupta.app.repository.BlogRepository;
import in.rishabh_gupta.app.repository.UserRepository;
import in.rishabh_gupta.app.services.BlogService;
import in.rishabh_gupta.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;

    @Override
    public Page<BlogResponse> index(String keyword, int pageNumber, int pageSize, BlogSortFields sortBy, SortDirection sortDir) {
        Sort sort = sortDir.equals(SortDirection.ASC) ? Sort.by(sortBy.name()).ascending() : Sort.by(sortBy.name()).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Blog> blogs = null;

        if (keyword == null || keyword.trim().isEmpty()) {
            blogs = this.blogRepository.findAll(pageable);
        } else {
            blogs = this.blogRepository.findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);
        }

        return blogs.map(this.blogMapper::toResponse);
    }

    @Override
    public Page<BlogResponse> index(Long userId, String keyword, int pageNumber, int pageSize, BlogSortFields sortBy, SortDirection sortDir) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(UserService.NOT_FOUND_MESSAGE));

        Sort sort = sortDir.equals(SortDirection.ASC) ? Sort.by(sortBy.name()).ascending() : Sort.by(sortBy.name()).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Blog> blogs = null;

        if (keyword == null || keyword.trim().isEmpty()) {
            blogs = this.blogRepository.findByUserId(userId, pageable);
        } else {
            blogs = this.blogRepository.findByUserIdAndNameContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(userId, keyword, userId, keyword, pageable);
        }

        return blogs.map(this.blogMapper::toResponse);
    }

    @Override
    public BlogResponse create(Long userId, BlogRequest blogRequest) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(UserService.NOT_FOUND_MESSAGE));

        Blog blog = this.blogMapper.toEntity(blogRequest);
        blog.setUser(user);

        return this.blogMapper.toResponse(this.blogRepository.save(blog));
    }

    @Override
    public BlogResponse show(Long userId, Long blogId) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(UserService.NOT_FOUND_MESSAGE));
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException(BlogService.NOT_FOUND_MESSAGE));

        return this.blogMapper.toResponse(blog);
    }

    @Override
    public BlogResponse update(Long userId, Long blogId, BlogRequest blogRequest) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(UserService.NOT_FOUND_MESSAGE));
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException(BlogService.NOT_FOUND_MESSAGE));
        blog.setName(blogRequest.getName());
        blog.setContent(blogRequest.getContent());

        return this.blogMapper.toResponse(this.blogRepository.save(blog));
    }

    @Override
    public BlogResponse delete(Long userId, Long blogId) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(UserService.NOT_FOUND_MESSAGE));
        Blog blog = this.blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException(BlogService.NOT_FOUND_MESSAGE));
        blog.setDeletedAt(LocalDateTime.now());

        return this.blogMapper.toResponse(this.blogRepository.save(blog));
    }

    @Override
    public BlogResponse restore(Long userId, Long blogId) {
        AppUser user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(UserService.NOT_FOUND_MESSAGE));
        Blog blog = this.blogRepository.findByIdAndDeletedAtIsNotNull(blogId);
        blog.setDeletedAt(null);

        return this.blogMapper.toResponse(this.blogRepository.save(blog));
    }
}
