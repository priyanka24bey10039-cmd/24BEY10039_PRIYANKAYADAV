package com.library.controller;

import com.library.dto.request.MemberRequest;
import com.library.dto.response.ApiResponse;
import com.library.dto.response.MemberResponse;
import com.library.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Member Management", description = "APIs for managing library members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "Register a new member")
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(@Valid @RequestBody MemberRequest request) {
        log.info("[MemberController] POST /api/v1/members — email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Member registered successfully", memberService.createMember(request)));
    }

    @GetMapping
    @Operation(summary = "Get all members (paginated)")
    public ResponseEntity<ApiResponse<Page<MemberResponse>>> getAllMembers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "memberName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("[MemberController] GET /api/v1/members — page: {}, size: {}", page, size);
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return ResponseEntity.ok(ApiResponse.success("Members fetched successfully",
                memberService.getAllMembers(PageRequest.of(page, size, sort))));
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "Get member by ID")
    public ResponseEntity<ApiResponse<MemberResponse>> getMemberById(@PathVariable String memberId) {
        log.info("[MemberController] GET /api/v1/members/{}", memberId);
        return ResponseEntity.ok(ApiResponse.success("Member fetched successfully", memberService.getMemberById(memberId)));
    }

    @PutMapping("/{memberId}")
    @Operation(summary = "Update member details")
    public ResponseEntity<ApiResponse<MemberResponse>> updateMember(
            @PathVariable String memberId, @Valid @RequestBody MemberRequest request) {
        log.info("[MemberController] PUT /api/v1/members/{}", memberId);
        return ResponseEntity.ok(ApiResponse.success("Member updated successfully", memberService.updateMember(memberId, request)));
    }

    @DeleteMapping("/{memberId}")
    @Operation(summary = "Delete a member")
    public ResponseEntity<ApiResponse<Void>> deleteMember(@PathVariable String memberId) {
        log.info("[MemberController] DELETE /api/v1/members/{}", memberId);
        memberService.deleteMember(memberId);
        return ResponseEntity.ok(ApiResponse.success("Member deleted successfully", null));
    }
}
