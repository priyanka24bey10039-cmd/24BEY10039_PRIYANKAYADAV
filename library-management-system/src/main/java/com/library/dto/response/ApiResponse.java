package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Generic API response wrapper for all endpoints.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Standard API response envelope")
public class ApiResponse<T> {

    @Schema(description = "Whether the request was successful", example = "true")
    private boolean success;

    @Schema(description = "Human-readable message", example = "Author created successfully")
    private String message;

    @Schema(description = "Response payload")
    private T data;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Timestamp of the response")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder().success(true).message(message).data(data).build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder().success(false).message(message).build();
    }
}
