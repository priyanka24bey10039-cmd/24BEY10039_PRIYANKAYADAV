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
 * Category Entity
 *
 * <p>Represents a book category/genre in the library system.
 * MongoDB Collection: {@code categories}</p>
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>One Category → Many Books (One-to-Many)</li>
 * </ul>
 */
@Document(collection = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    private String categoryId;

    @Indexed(unique = true)
    @Field("category_name")
    private String categoryName;

    @Field("description")
    private String description;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
