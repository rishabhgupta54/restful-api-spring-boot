package in.rishabh_gupta.app.repository;

import in.rishabh_gupta.app.dto.request.UserRequest;
import in.rishabh_gupta.app.entity.AppUser;
import jakarta.validation.constraints.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Page<AppUser> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String lastName, String email, Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE id = :id AND deleted_at IS NOT NULL", nativeQuery = true)
    AppUser findByIdAndDeletedAtIsNotNull(Long id);

    AppUser findByEmail(String email);
}
