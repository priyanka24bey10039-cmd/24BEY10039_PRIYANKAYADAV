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
 * Member Entity
 *
 * <p>Represents a library member.
 * MongoDB Collection: {@code members}</p>
 *
 * <p>Relationships:</p>
 * <ul>
 *   <li>One Member → One LibraryCard (One-to-One)</li>
 *   <li>Many Members ↔ Many Books (via IssueRecord)</li>
 * </ul>
 */
@Document(collection = "members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private String memberId;

    @Field("member_name")
    private String memberName;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("phone")
    private String phone;

    @Field("address")
    private String address;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;
}
