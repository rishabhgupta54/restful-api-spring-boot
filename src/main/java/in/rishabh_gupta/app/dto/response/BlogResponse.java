package in.rishabh_gupta.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponse {
    private Long id;
    private String name;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
