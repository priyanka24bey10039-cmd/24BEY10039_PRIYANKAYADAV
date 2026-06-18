package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for returning Author data in API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Author details returned in API responses")
public class AuthorResponse {

    @Schema(description = "Unique identifier of the author", example = "64b7f1a2c3d4e5f6a7b8c9d0")
    private String authorId;

    @Schema(description = "Full name of the author", example = "George R.R. Martin")
    private String authorName;

    @Schema(description = "Author's email address", example = "george.martin@authors.com")
    private String email;

    @Schema(description = "Author's phone number", example = "+919876543210")
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Timestamp when the record was created")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Timestamp when the record was last updated")
    private LocalDateTime updatedAt;
}
