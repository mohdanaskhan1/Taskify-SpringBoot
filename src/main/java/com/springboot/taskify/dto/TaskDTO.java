package com.springboot.taskify.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TaskDTO {
    private Long id;

    @NotBlank(message = "Task title is required")
    @Size(max = 100, message = "Task title must not exceed 100 characters")
    private String title;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private Instant dueDate;

    @NotBlank(message = "Priority is required")
    private String priority;

    @NotBlank(message = "Status is required")
    private String status;


    private List<CommentDTO> comments;

    @NotNull(message = "User ID is required")
    private Long userId;
    private List<String> tags;
}
