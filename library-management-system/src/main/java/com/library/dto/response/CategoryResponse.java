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
@Schema(description = "Category details returned in API responses")
public class CategoryResponse {

    @Schema(description = "Unique identifier of the category", example = "64b7f1a2c3d4e5f6a7b8c9d1")
    private String categoryId;

    @Schema(description = "Name of the category", example = "Science Fiction")
    private String categoryName;

    @Schema(description = "Description of the category", example = "Books exploring futuristic science and technology")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
