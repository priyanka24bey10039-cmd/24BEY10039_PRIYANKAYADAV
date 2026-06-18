package com.library.controller;

import com.library.dto.request.BookRequest;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.BookResponse;
import com.library.service.BookService;
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
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book Management", description = "APIs for managing books — CRUD + search + availability")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Create a new book", description = "ISBN must be unique. authorId and categoryId must reference existing records.")
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody BookRequest request) {
        log.info("[BookController] POST /api/v1/books");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book created successfully", bookService.createBook(request)));
    }

    @GetMapping
    @Operation(summary = "Get all books (paginated & sortable)")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return ResponseEntity.ok(ApiResponse.success("Books fetched successfully",
                bookService.getAllBooks(PageRequest.of(page, size, sort))));
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "Get book by ID")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable String bookId) {
        return ResponseEntity.ok(ApiResponse.success("Book fetched successfully", bookService.getBookById(bookId)));
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "Update a book")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(
            @PathVariable String bookId, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", bookService.updateBook(bookId, request)));
    }

    @DeleteMapping("/{bookId}")
    @Operation(summary = "Delete a book")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable String bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }

    // ── Search & Filter ────────────────────────────────────────────────────────

    @GetMapping("/search/title")
    @Operation(summary = "Search books by title", description = "Case-insensitive partial match on book title.")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> searchByTitle(
            @Parameter(description = "Title keyword", example = "Thrones") @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("[BookController] Search by title: '{}'", title);
        return ResponseEntity.ok(ApiResponse.success("Books found",
                bookService.searchByTitle(title, PageRequest.of(page, size))));
    }

    @GetMapping("/search/category/{categoryId}")
    @Operation(summary = "Search books by category ID")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> searchByCategory(
            @PathVariable String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Books fetched by category",
                bookService.searchByCategory(categoryId, PageRequest.of(page, size))));
    }

    @GetMapping("/search/author/{authorId}")
    @Operation(summary = "Search books by author ID")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> searchByAuthor(
            @PathVariable String authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Books fetched by author",
                bookService.searchByAuthor(authorId, PageRequest.of(page, size))));
    }

    @GetMapping("/available")
    @Operation(summary = "Get only available books", description = "Returns books where availableQuantity > 0.")
    public ResponseEntity<ApiResponse<Page<BookResponse>>> getAvailableBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Available books fetched",
                bookService.getAvailableBooks(PageRequest.of(page, size))));
    }
}
