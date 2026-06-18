package com.library.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * Author Entity
 *
 * <p>Represents a book author in the library system.
 * MongoDB Collection: {@code authors}</p>
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>One Author → Many Books (One-to-Many)</li>
 * </ul>
 */
@Document(collection = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    private String authorId;

    @Field("author_name")
    private String authorName;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("phone")
    private String phone;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
