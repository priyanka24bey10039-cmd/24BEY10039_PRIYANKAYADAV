package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for issuing a book to a member.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for issuing a book")
public class IssueRecordRequest {

    @NotBlank(message = "Member ID is required")
    @Schema(description = "MongoDB ID of the member", example = "64b7f1a2c3d4e5f6a7b8c9d2", requiredMode = Schema.RequiredMode.REQUIRED)
    private String memberId;

    @NotBlank(message = "Book ID is required")
    @Schema(description = "MongoDB ID of the book", example = "64b7f1a2c3d4e5f6a7b8c9d3", requiredMode = Schema.RequiredMode.REQUIRED)
    private String bookId;

    @NotNull(message = "Issue date is required")
    @Schema(description = "Date the book is being issued", example = "2024-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate issueDate;
}
