package in.rishabh_gupta.app.dto.request;

import in.rishabh_gupta.app.validation_groups.UserCreate;
import in.rishabh_gupta.app.validation_groups.UserUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @Email
    private String email;

    @NotBlank(groups = UserCreate.class)
    @Size(min = 8, message = "size must be greater than or equal to 8", groups = {UserCreate.class, UserUpdate.class})
    private String password;
}
