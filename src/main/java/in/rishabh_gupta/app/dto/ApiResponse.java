package in.rishabh_gupta.app.dto;

import in.rishabh_gupta.app.enums.ApiResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private ApiResponseStatus status;
    private Map<String, String> errors;
    private T data;
    private PageMeta pageMeta;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.Success)
                .errors(null)
                .data(data)
                .pageMeta(null)
                .build();
    }

    public static <T> ApiResponse<T> success(T data, Page<?> page) {
        PageMeta meta = null;
        if (page != null) {
            meta = new PageMeta(page.getTotalElements(), page.getTotalPages());
        }
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.Success)
                .data(data)
                .errors(null)
                .pageMeta(meta)
                .build();
    }

    public static <T> ApiResponse<T> error(String error) {
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.Error)
                .data(null)
                .errors(Map.of("error", error))
                .pageMeta(null)
                .build();
    }

    public static <T> ApiResponse<T> validationError(Map<String, String> error) {
        return ApiResponse.<T>builder()
                .status(ApiResponseStatus.Error)
                .data(null)
                .errors(error)
                .pageMeta(null)
                .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageMeta {
        private long totalRecords;
        private long totalPages;
    }
}
