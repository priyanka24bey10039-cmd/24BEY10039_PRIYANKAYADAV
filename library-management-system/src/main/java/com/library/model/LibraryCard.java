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
 * LibraryCard Entity
 *
 * <p>Represents a library membership card issued to a member.
 * MongoDB Collection: {@code library_cards}</p>
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>One LibraryCard → One Member (One-to-One via {@code memberId})</li>
 * </ul>
 */
@Document(collection = "library_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LibraryCard {

    @Id
    private String cardId;

    @Field("issue_date")
    private LocalDate issueDate;

    @Field("expiry_date")
    private LocalDate expiryDate;

    /**
     * Reference to Member — enforced as unique to implement One-to-One relationship.
     * Each member can have at most one active library card.
     */
    @Indexed(unique = true)
    @Field("member_id")
    private String memberId;

    @Field("is_active")
    @Builder.Default
    private boolean active = true;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
