package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.model.IssueRecord.IssueStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Issue record details returned in API responses")
public class IssueRecordResponse {
    private String issueId;
    private String memberId;
    private String memberName;
    private String bookId;
    private String bookTitle;
    @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate issueDate;
    @JsonFormat(pattern = "yyyy-MM-dd") private LocalDate returnDate;
    private IssueStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime updatedAt;
}
