package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Book details returned in API responses")
public class BookResponse {

    @Schema(description = "Unique identifier of the book", example = "64b7f1a2c3d4e5f6a7b8c9d3")
    private String bookId;

    @Schema(description = "Title of the book", example = "A Game of Thrones")
    private String title;

    @Schema(description = "ISBN-10 or ISBN-13", example = "978-0553103540")
    private String isbn;

    @Schema(description = "Year the book was published", example = "1996")
    private Integer publicationYear;

    @Schema(description = "Total number of copies", example = "5")
    private Integer quantity;

    @Schema(description = "Currently available copies for borrowing", example = "3")
    private Integer availableQuantity;

    @Schema(description = "Author ID reference", example = "64b7f1a2c3d4e5f6a7b8c9d0")
    private String authorId;

    @Schema(description = "Author's name (denormalized for convenience)")
    private String authorName;

    @Schema(description = "Category ID reference", example = "64b7f1a2c3d4e5f6a7b8c9d1")
    private String categoryId;

    @Schema(description = "Category name (denormalized for convenience)")
    private String categoryName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
