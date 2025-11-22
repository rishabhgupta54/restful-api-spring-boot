package in.rishabh_gupta.app.mapper;

import in.rishabh_gupta.app.dto.request.BlogRequest;
import in.rishabh_gupta.app.dto.response.BlogResponse;
import in.rishabh_gupta.app.entity.Blog;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {
    public Blog toEntity(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setName(blogRequest.getName());
        blog.setContent(blogRequest.getContent());

        return blog;
    }

    public BlogResponse toResponse(Blog blog) {
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setId(blog.getId());
        blogResponse.setName(blog.getName());
        blogResponse.setContent(blog.getContent());
        blogResponse.setCreatedAt(blog.getCreatedAt());
        blogResponse.setUpdatedAt(blog.getUpdatedAt());

        return blogResponse;
    }
}
