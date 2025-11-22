package in.rishabh_gupta.app.services;

import in.rishabh_gupta.app.dto.request.BlogRequest;
import in.rishabh_gupta.app.dto.response.BlogResponse;
import in.rishabh_gupta.app.enums.BlogSortFields;
import in.rishabh_gupta.app.enums.SortDirection;
import org.springframework.data.domain.Page;

public interface BlogService {
    String NOT_FOUND_MESSAGE = "Blog not found";

    Page<BlogResponse> index(String keyword, int pageNumber, int pageSize, BlogSortFields sortBy, SortDirection sortDir);

    Page<BlogResponse> index(Long userId, String keyword, int pageNumber, int pageSize, BlogSortFields sortBy, SortDirection sortDir);

    BlogResponse create(Long userId, BlogRequest blogRequest);

    BlogResponse show(Long userId, Long blogId);

    BlogResponse update(Long userId, Long blogId, BlogRequest blogRequest);

    BlogResponse delete(Long userId, Long blogId);

    BlogResponse restore(Long userId, Long blogId);
}
