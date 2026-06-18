package com.library.controller;

import com.library.dto.request.LibraryCardRequest;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.LibraryCardResponse;
import com.library.service.LibraryCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/library-cards")
@RequiredArgsConstructor
@Tag(name = "Library Card Management", description = "APIs for issuing and managing library cards (One-to-One with Member)")
public class LibraryCardController {

    private final LibraryCardService libraryCardService;

    @PostMapping
    @Operation(summary = "Issue a library card to a member",
               description = "Each member can have only one library card (One-to-One relationship).")
    public ResponseEntity<ApiResponse<LibraryCardResponse>> createCard(@Valid @RequestBody LibraryCardRequest request) {
        log.info("[LibraryCardController] POST /api/v1/library-cards — memberId: {}", request.getMemberId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Library card issued successfully", libraryCardService.createCard(request)));
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Get library card by card ID")
    public ResponseEntity<ApiResponse<LibraryCardResponse>> getCardById(@PathVariable String cardId) {
        log.info("[LibraryCardController] GET /api/v1/library-cards/{}", cardId);
        return ResponseEntity.ok(ApiResponse.success("Card fetched successfully", libraryCardService.getCardById(cardId)));
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get library card by member ID",
               description = "Fetches the card associated with a specific member.")
    public ResponseEntity<ApiResponse<LibraryCardResponse>> getCardByMemberId(@PathVariable String memberId) {
        log.info("[LibraryCardController] GET /api/v1/library-cards/member/{}", memberId);
        return ResponseEntity.ok(ApiResponse.success("Card fetched successfully", libraryCardService.getCardByMemberId(memberId)));
    }

    @PutMapping("/{cardId}")
    @Operation(summary = "Update library card details (e.g. extend expiry date)")
    public ResponseEntity<ApiResponse<LibraryCardResponse>> updateCard(
            @PathVariable String cardId, @Valid @RequestBody LibraryCardRequest request) {
        log.info("[LibraryCardController] PUT /api/v1/library-cards/{}", cardId);
        return ResponseEntity.ok(ApiResponse.success("Card updated successfully", libraryCardService.updateCard(cardId, request)));
    }

    @DeleteMapping("/{cardId}")
    @Operation(summary = "Delete / revoke a library card")
    public ResponseEntity<ApiResponse<Void>> deleteCard(@PathVariable String cardId) {
        log.info("[LibraryCardController] DELETE /api/v1/library-cards/{}", cardId);
        libraryCardService.deleteCard(cardId);
        return ResponseEntity.ok(ApiResponse.success("Card deleted successfully", null));
    }
}
