package in.rishabh_gupta.app.exception;

import in.rishabh_gupta.app.dto.ApiResponse;
import in.rishabh_gupta.app.enums.ApiResponseStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseNotFound(ResourceNotFoundException exception) {
        ApiResponse<Object> response = ApiResponse.error(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateCategoryException(DuplicateCategoryException exception) {
        ApiResponse<Object> response = ApiResponse.error(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmailException(DuplicateEmailException exception) {
        ApiResponse<Object> response = ApiResponse.error(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmailException(HttpMessageNotReadableException exception) {
        ApiResponse<Object> response = ApiResponse.error(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception exception, HttpServletRequest request) {
        String uri = request.getRequestURI();

        // Swagger and Actuator endpoints ignore kar do
        if (uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui") || uri.startsWith("/actuator")) {
            // Let Swagger handle its own errors
            throw new RuntimeException(exception);
        }

        ApiResponse<Object> response = ApiResponse.<Object>builder().status(ApiResponseStatus.Error).errors(null).data(null).pageMeta(null).build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationErrors(org.springframework.web.bind.MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));

        ApiResponse<Object> response = ApiResponse.validationError(errorMap);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
