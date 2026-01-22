package com.project.BlogSystem.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Enter your content, it cannot be blank")
    private String content;

    @NotBlank(message = "Enter your Post status, it cannot be blank")
    @Size(max = 9, message = "Post status must 9 characters")
    private String status;
}
