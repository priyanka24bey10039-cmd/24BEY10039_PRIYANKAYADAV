package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for creating or updating a LibraryCard.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating a library card")
public class LibraryCardRequest {

    @NotNull(message = "Issue date is required")
    @Schema(description = "Date the card was issued", example = "2024-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate issueDate;

    @NotNull(message = "Expiry date is required")
    @FutureOrPresent(message = "Expiry date must be today or in the future")
    @Schema(description = "Expiry date of the library card", example = "2025-01-15", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate expiryDate;

    @NotBlank(message = "Member ID is required")
    @Schema(description = "MongoDB ID of the member this card belongs to", example = "64b7f1a2c3d4e5f6a7b8c9d2", requiredMode = Schema.RequiredMode.REQUIRED)
    private String memberId;
}
