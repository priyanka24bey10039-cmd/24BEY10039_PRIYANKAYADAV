package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Library card details returned in API responses")
public class LibraryCardResponse {
    private String cardId;
    @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate issueDate;
    @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate expiryDate;
    private String memberId;
    private String memberName;
    private boolean active;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime updatedAt;
}
