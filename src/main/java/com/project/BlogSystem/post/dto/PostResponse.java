package com.project.BlogSystem.post.dto;

import com.project.BlogSystem.profile.dto.ProfileResponse;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private String title;
    
    private String content;

    private LocalDateTime creationDate;

    private ProfileResponse author;
}
