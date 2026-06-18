package com.library.controller;

import com.library.dto.request.IssueRecordRequest;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.IssueRecordResponse;
import com.library.service.IssueRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
@Tag(name = "Issue Record Management", description = "APIs for issuing and returning books")
public class IssueRecordController {

    private final IssueRecordService issueRecordService;

    @PostMapping
    @Operation(summary = "Issue a book to a member",
               description = "Verifies member exists, book exists, and availableQuantity > 0. Decrements availableQuantity by 1.")
    public ResponseEntity<ApiResponse<IssueRecordResponse>> issueBook(@Valid @RequestBody IssueRecordRequest request) {
        log.info("[IssueRecordController] POST /api/v1/issues — member: {}, book: {}", request.getMemberId(), request.getBookId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book issued successfully", issueRecordService.issueBook(request)));
    }

    @PutMapping("/{issueId}/return")
    @Operation(summary = "Return a book",
               description = "Marks the issue record as RETURNED and increments the book's availableQuantity by 1.")
    public ResponseEntity<ApiResponse<IssueRecordResponse>> returnBook(@PathVariable String issueId) {
        log.info("[IssueRecordController] PUT /api/v1/issues/{}/return", issueId);
        return ResponseEntity.ok(ApiResponse.success("Book returned successfully", issueRecordService.returnBook(issueId)));
    }

    @GetMapping("/{issueId}")
    @Operation(summary = "Get issue record by ID")
    public ResponseEntity<ApiResponse<IssueRecordResponse>> getIssueById(@PathVariable String issueId) {
        return ResponseEntity.ok(ApiResponse.success("Issue record fetched", issueRecordService.getIssueById(issueId)));
    }

    @GetMapping
    @Operation(summary = "Get full issue history (paginated)")
    public ResponseEntity<ApiResponse<Page<IssueRecordResponse>>> getAllIssueHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Issue history fetched",
                issueRecordService.getAllIssueHistory(PageRequest.of(page, size))));
    }

    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get all issues by a specific member")
    public ResponseEntity<ApiResponse<Page<IssueRecordResponse>>> getIssuesByMember(
            @PathVariable String memberId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Member issues fetched",
                issueRecordService.getIssuesByMember(memberId, PageRequest.of(page, size))));
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get all issues for a specific book")
    public ResponseEntity<ApiResponse<Page<IssueRecordResponse>>> getIssuesByBook(
            @PathVariable String bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Book issues fetched",
                issueRecordService.getIssuesByBook(bookId, PageRequest.of(page, size))));
    }
}
