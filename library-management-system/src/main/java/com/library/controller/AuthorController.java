package com.library.controller;

import com.library.dto.request.AuthorRequest;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.AuthorResponse;
import com.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Author Management", description = "APIs for managing book authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @Operation(summary = "Create a new author", description = "Creates a new author record. Email must be unique.")
    public ResponseEntity<ApiResponse<AuthorResponse>> createAuthor(@Valid @RequestBody AuthorRequest request) {
        log.info("[AuthorController] POST /api/v1/authors");
        AuthorResponse response = authorService.createAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Author created successfully", response));
    }

    @GetMapping
    @Operation(summary = "Get all authors", description = "Returns a paginated list of all authors.")
    public ResponseEntity<ApiResponse<Page<AuthorResponse>>> getAllAuthors(
            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field", example = "authorName") @RequestParam(defaultValue = "authorName") String sortBy,
            @Parameter(description = "Sort direction", example = "asc") @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("[AuthorController] GET /api/v1/authors?page={}&size={}", page, size);
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Page<AuthorResponse> authors = authorService.getAllAuthors(PageRequest.of(page, size, sort));
        return ResponseEntity.ok(ApiResponse.success("Authors fetched successfully", authors));
    }

    @GetMapping("/{authorId}")
    @Operation(summary = "Get author by ID")
    public ResponseEntity<ApiResponse<AuthorResponse>> getAuthorById(
            @Parameter(description = "Author ID", example = "64b7f1a2c3d4e5f6a7b8c9d0") @PathVariable String authorId) {
        log.info("[AuthorController] GET /api/v1/authors/{}", authorId);
        return ResponseEntity.ok(ApiResponse.success("Author fetched successfully", authorService.getAuthorById(authorId)));
    }

    @PutMapping("/{authorId}")
    @Operation(summary = "Update an author")
    public ResponseEntity<ApiResponse<AuthorResponse>> updateAuthor(
            @PathVariable String authorId, @Valid @RequestBody AuthorRequest request) {
        log.info("[AuthorController] PUT /api/v1/authors/{}", authorId);
        return ResponseEntity.ok(ApiResponse.success("Author updated successfully", authorService.updateAuthor(authorId, request)));
    }

    @DeleteMapping("/{authorId}")
    @Operation(summary = "Delete an author")
    public ResponseEntity<ApiResponse<Void>> deleteAuthor(@PathVariable String authorId) {
        log.info("[AuthorController] DELETE /api/v1/authors/{}", authorId);
        authorService.deleteAuthor(authorId);
        return ResponseEntity.ok(ApiResponse.success("Author deleted successfully", null));
    }
}
