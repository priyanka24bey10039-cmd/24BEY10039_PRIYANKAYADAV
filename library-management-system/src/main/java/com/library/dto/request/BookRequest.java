package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a Book.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating a book")
public class BookRequest {

    @NotBlank(message = "Book title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Schema(description = "Title of the book", example = "A Game of Thrones", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Pattern(
        regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
        message = "Invalid ISBN format. Use ISBN-10 or ISBN-13"
    )
    @Schema(description = "ISBN-10 or ISBN-13", example = "978-0553103540", requiredMode = Schema.RequiredMode.REQUIRED)
    private String isbn;

    @Min(value = 1000, message = "Publication year must be after 1000")
    @Max(value = 2100, message = "Publication year cannot exceed 2100")
    @Schema(description = "Year the book was published", example = "1996")
    private Integer publicationYear;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Total number of copies in the library", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @NotBlank(message = "Author ID is required")
    @Schema(description = "MongoDB ID of the author", example = "64b7f1a2c3d4e5f6a7b8c9d0", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorId;

    @NotBlank(message = "Category ID is required")
    @Schema(description = "MongoDB ID of the category", example = "64b7f1a2c3d4e5f6a7b8c9d1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoryId;
}
