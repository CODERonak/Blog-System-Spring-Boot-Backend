package com.project.BlogSystem.profile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 30, message = "Name must be between 2 and 50 characters")
    private String name;

    @Size(max = 200, message = "Bio cannot be longer than 200 characters")
    private String bio;
}
