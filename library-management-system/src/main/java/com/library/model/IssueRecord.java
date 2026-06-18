package com.library.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * IssueRecord Entity
 *
 * <p>Represents the issuance of a book to a member. Acts as a junction
 * collection implementing the Many-to-Many relationship between Members and Books.
 * MongoDB Collection: {@code issue_records}</p>
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>Many IssueRecords → One Member (via {@code memberId})</li>
 *   <li>Many IssueRecords → One Book (via {@code bookId})</li>
 *   <li>Together, these implement Members ↔ Books Many-to-Many</li>
 * </ul>
 */
@Document(collection = "issue_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueRecord {

    @Id
    private String issueId;

    /** Reference to the Member who borrowed the book */
    @Indexed
    @Field("member_id")
    private String memberId;

    /** Reference to the Book that was borrowed */
    @Indexed
    @Field("book_id")
    private String bookId;

    @Field("issue_date")
    private LocalDate issueDate;

    @Field("return_date")
    private LocalDate returnDate;

    /**
     * Current status of the issue record.
     * @see IssueStatus
     */
    @Field("status")
    @Builder.Default
    private IssueStatus status = IssueStatus.ISSUED;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    /**
     * Enum representing the lifecycle status of a book issue.
     */
    public enum IssueStatus {
        /** Book has been issued and not yet returned */
        ISSUED,
        /** Book has been returned by the member */
        RETURNED,
        /** Book is overdue (not returned by expected date) */
        OVERDUE
    }
}
