package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating an Author.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating an author")
public class AuthorRequest {

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    @Schema(description = "Full name of the author", example = "George R.R. Martin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Author's email address", example = "george.martin@authors.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be 10-15 digits")
    @Schema(description = "Author's phone number", example = "+919876543210")
    private String phone;
}
