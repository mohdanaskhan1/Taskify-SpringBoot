package com.springboot.taskify.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CommentDTO {
    private Long id;

    @NotBlank(message = "Comment text must not be empty")
    @Size(max = 2000, message = "Comment text must not exceed 2000 characters")
    private String commentText;
}
