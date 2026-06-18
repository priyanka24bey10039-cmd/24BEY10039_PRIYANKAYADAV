package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a Category.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating a category")
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    @Schema(description = "Name of the category", example = "Science Fiction", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(description = "Detailed description of the category", example = "Books exploring futuristic science and technology")
    private String description;
}
