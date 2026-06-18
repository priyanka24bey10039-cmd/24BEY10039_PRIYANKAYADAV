package com.library.service.impl;

import com.library.dto.request.IssueRecordRequest;
import com.library.dto.response.IssueRecordResponse;
import com.library.exception.InvalidOperationException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.IssueRecord;
import com.library.model.IssueRecord.IssueStatus;
import com.library.model.Member;
import com.library.repository.BookRepository;
import com.library.repository.IssueRecordRepository;
import com.library.repository.MemberRepository;
import com.library.service.IssueRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueRecordServiceImpl implements IssueRecordService {

    private final IssueRecordRepository issueRecordRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    /**
     * Issues a book to a member.
     *
     * <p>Business rules enforced:</p>
     * <ol>
     *   <li>Member must exist</li>
     *   <li>Book must exist</li>
     *   <li>Book must have availableQuantity &gt; 0</li>
     *   <li>Member must not have already issued this book without returning</li>
     *   <li>Decrements availableQuantity by 1</li>
     * </ol>
     */
    @Override
    public IssueRecordResponse issueBook(IssueRecordRequest request) {
        log.info("[IssueRecordService] Issuing book {} to member {}", request.getBookId(), request.getMemberId());

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", request.getMemberId()));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", request.getBookId()));

        // Business rule: book must be available
        if (book.getAvailableQuantity() <= 0) {
            throw new InvalidOperationException(
                    "Book '" + book.getTitle() + "' is not available. All copies are currently issued.");
        }

        // Business rule: member shouldn't have the same book already issued
        issueRecordRepository.findByMemberIdAndBookIdAndStatus(
                request.getMemberId(), request.getBookId(), IssueStatus.ISSUED)
                .ifPresent(existing -> {
                    throw new InvalidOperationException(
                            "Member already has an active issue for book '" + book.getTitle() + "'");
                });

        // Decrement available quantity
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);
        log.info("[IssueRecordService] Book '{}' available quantity reduced to {}", book.getTitle(), book.getAvailableQuantity());

        IssueRecord record = IssueRecord.builder()
                .memberId(member.getMemberId())
                .bookId(book.getBookId())
                .issueDate(request.getIssueDate())
                .status(IssueStatus.ISSUED)
                .build();
        IssueRecord saved = issueRecordRepository.save(record);
        log.info("[IssueRecordService] Issue record created: {}", saved.getIssueId());
        return toResponse(saved, member, book);
    }

    /**
     * Returns a book previously issued.
     *
     * <p>Business rules enforced:</p>
     * <ol>
     *   <li>Issue record must exist and be in ISSUED state</li>
     *   <li>Increments availableQuantity by 1</li>
     *   <li>Updates IssueRecord status to RETURNED</li>
     * </ol>
     */
    @Override
    public IssueRecordResponse returnBook(String issueId) {
        log.info("[IssueRecordService] Processing return for issue: {}", issueId);
        IssueRecord record = findById(issueId);

        if (record.getStatus() == IssueStatus.RETURNED) {
            throw new InvalidOperationException("Book has already been returned for issue ID: " + issueId);
        }

        Book book = bookRepository.findById(record.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", record.getBookId()));
        Member member = memberRepository.findById(record.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", record.getMemberId()));

        // Increment available quantity
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
        log.info("[IssueRecordService] Book '{}' available quantity restored to {}", book.getTitle(), book.getAvailableQuantity());

        // Update record
        record.setStatus(IssueStatus.RETURNED);
        record.setReturnDate(LocalDate.now());
        IssueRecord updated = issueRecordRepository.save(record);
        log.info("[IssueRecordService] Book returned. Issue record updated: {}", issueId);
        return toResponse(updated, member, book);
    }

    @Override
    public IssueRecordResponse getIssueById(String issueId) {
        IssueRecord record = findById(issueId);
        Member member = memberRepository.findById(record.getMemberId()).orElse(null);
        Book book = bookRepository.findById(record.getBookId()).orElse(null);
        return toResponse(record, member, book);
    }

    @Override
    public Page<IssueRecordResponse> getAllIssueHistory(Pageable pageable) {
        log.info("[IssueRecordService] Fetching all issue history");
        return issueRecordRepository.findAll(pageable).map(this::toResponseWithLookup);
    }

    @Override
    public Page<IssueRecordResponse> getIssuesByMember(String memberId, Pageable pageable) {
        log.info("[IssueRecordService] Fetching issues for member: {}", memberId);
        memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", memberId));
        return issueRecordRepository.findByMemberId(memberId, pageable).map(this::toResponseWithLookup);
    }

    @Override
    public Page<IssueRecordResponse> getIssuesByBook(String bookId, Pageable pageable) {
        log.info("[IssueRecordService] Fetching issues for book: {}", bookId);
        bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        return issueRecordRepository.findByBookId(bookId, pageable).map(this::toResponseWithLookup);
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    private IssueRecord findById(String id) {
        return issueRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IssueRecord", "id", id));
    }

    private IssueRecordResponse toResponseWithLookup(IssueRecord record) {
        Member member = memberRepository.findById(record.getMemberId()).orElse(null);
        Book book = bookRepository.findById(record.getBookId()).orElse(null);
        return toResponse(record, member, book);
    }

    private IssueRecordResponse toResponse(IssueRecord record, Member member, Book book) {
        return IssueRecordResponse.builder()
                .issueId(record.getIssueId())
                .memberId(record.getMemberId())
                .memberName(member != null ? member.getMemberName() : null)
                .bookId(record.getBookId())
                .bookTitle(book != null ? book.getTitle() : null)
                .issueDate(record.getIssueDate())
                .returnDate(record.getReturnDate())
                .status(record.getStatus())
                .createdAt(record.getCreatedAt())
                .updatedAt(record.getUpdatedAt())
                .build();
    }
}
