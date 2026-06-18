package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Member details returned in API responses")
public class MemberResponse {
    private String memberId;
    private String memberName;
    private String email;
    private String phone;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime updatedAt;
}
