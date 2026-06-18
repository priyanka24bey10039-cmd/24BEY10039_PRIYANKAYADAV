package com.library.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Book Entity
 *
 * <p>Represents a book in the library system.
 * MongoDB Collection: {@code books}</p>
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>Many Books → One Author (Many-to-One via {@code authorId})</li>
 *   <li>Many Books → One Category (Many-to-One via {@code categoryId})</li>
 *   <li>Many Books ↔ Many Members (via IssueRecord)</li>
 * </ul>
 */
@Document(collection = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    private String bookId;

    @TextIndexed
    @Field("title")
    private String title;

    @Indexed(unique = true)
    @Field("isbn")
    private String isbn;

    @Field("publication_year")
    private Integer publicationYear;

    @Field("quantity")
    private Integer quantity;

    @Field("available_quantity")
    private Integer availableQuantity;

    /** Reference to Author (Many-to-One relationship) */
    @Indexed
    @Field("author_id")
    private String authorId;

    /** Reference to Category (Many-to-One relationship) */
    @Indexed
    @Field("category_id")
    private String categoryId;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
