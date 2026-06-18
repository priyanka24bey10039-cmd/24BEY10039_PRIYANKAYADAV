package com.library.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a Member.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating a member")
public class MemberRequest {

    @NotBlank(message = "Member name is required")
    @Size(min = 2, max = 100, message = "Member name must be between 2 and 100 characters")
    @Schema(description = "Full name of the member", example = "Rahul Sharma", requiredMode = Schema.RequiredMode.REQUIRED)
    private String memberName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Member's email address", example = "rahul.sharma@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number must be 10-15 digits")
    @Schema(description = "Member's phone number", example = "+919876543210")
    private String phone;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Schema(description = "Member's residential address", example = "123, MG Road, Bangalore, Karnataka - 560001")
    private String address;
}
