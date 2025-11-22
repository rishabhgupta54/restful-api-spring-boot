package in.rishabh_gupta.app.repository;

import in.rishabh_gupta.app.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findByUserId(Long userId, Pageable pageable);

    Page<Blog> findByNameContainingIgnoreCaseOrContentContainingIgnoreCase(String name, String content, Pageable pageable);

    Page<Blog> findByUserIdAndNameContainingIgnoreCaseOrUserIdAndContentContainingIgnoreCase(Long userId1, String name, Long userId2, String content, Pageable pageable);

    @Query(value = "SELECT * FROM blogs WHERE id = :id AND deleted_at IS NOT NULL", nativeQuery = true)
    Blog findByIdAndDeletedAtIsNotNull(Long id);
}
