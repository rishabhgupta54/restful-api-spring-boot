package in.rishabh_gupta.app.repository;

import in.rishabh_gupta.app.entity.AppUser;
import in.rishabh_gupta.app.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT * FROM categories WHERE id = :id AND deleted_at IS NOT NULL", nativeQuery = true)
    Category findByIdAndDeletedAtIsNotNull(Long id);

    Category findByName(String name);
}
